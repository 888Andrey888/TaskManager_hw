package com.example.taskmanager.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.task.TaskAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter = TaskAdapter(this::onClickItem)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        val tasks = App.db.taskDao().getAll()
        adapter.addTasks(tasks)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    private fun onClickItem(task: Task) {
        showAlertDialog(task)
    }

    private fun showAlertDialog(task: Task) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Delete").setMessage("Delete this task?").setCancelable(true)
            .setPositiveButton("Yes") { dialog, which ->
                App.db.taskDao().delete(task)
                val tasks = App.db.taskDao().getAll()
                adapter.addTasks(tasks)
            }.setNegativeButton("No") { dialog, which -> }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}