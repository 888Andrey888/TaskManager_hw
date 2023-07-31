package com.example.taskmanager.ui.task

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Task

class TaskAdapter(
    val onLongClickItem: (task: Task) -> Unit,
    val onClickItem: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val list = arrayListOf<Task>()

    fun addTasks(tasks: List<Task>) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {

        fun bind(task: Task) = with(binding) {
            changeViewColor()

            tvTitle.text = task.title
            tvDesc.text = task.descriptor
            itemView.setOnLongClickListener {
                onLongClickItem(task)
                true
            }
            itemView.setOnClickListener {
                onClickItem(task)
            }
        }

        private fun ItemTaskBinding.changeViewColor() {
            if (adapterPosition % 2 == 0) {
                llContainerItemTask.setBackgroundColor(Color.BLACK)
                tvTitle.setTextColor(Color.WHITE)
                tvDesc.setTextColor(Color.WHITE)
            } else {
                llContainerItemTask.setBackgroundColor(Color.WHITE)
                tvTitle.setTextColor(Color.BLACK)
                tvDesc.setTextColor(Color.BLACK)
            }
        }
    }

}


