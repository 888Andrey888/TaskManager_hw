package com.example.taskmanager.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.MainActivity
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.home.HomeFragment.Companion.TASK_KEY
import com.example.taskmanager.utils.EditTextEmptyLineException
import com.example.taskmanager.utils.checkingForEmptyLine

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        task = arguments?.getSerializable(TASK_KEY) as Task?

        initFields()
        initListener()
    }

    private fun initFields() = with(binding) {
        if (task != null) {
            etTitle.setText(task?.title)
            etDesc.setText(task?.descriptor)
            btnAddTask.text = getString(R.string.update)
        }
    }

    private fun initListener() = with(binding) {
        btnAddTask.setOnClickListener {
            try {
                if (task == null)
                    insertTaskInDb()
                else
                    updateTaskInDb()
                findNavController().navigateUp()
            } catch (e: EditTextEmptyLineException) {
                etTitle.requestFocus()
                etTitle.error = e.message
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateTaskInDb() {
        val data = task?.copy(
            title = binding.etTitle.checkingForEmptyLine(),
            descriptor = binding.etDesc.text.toString()
        )
        data?.let { App.db.taskDao().update(it) }
    }

    private fun insertTaskInDb() {
        val task = Task(
            title = binding.etTitle.checkingForEmptyLine(),
            descriptor = binding.etDesc.text.toString()
        )
        App.db.taskDao().insert(task)
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}