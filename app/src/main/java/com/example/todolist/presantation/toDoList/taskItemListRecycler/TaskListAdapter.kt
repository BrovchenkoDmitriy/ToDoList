package com.example.todolist.presantation.toDoList.taskItemListRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.todolist.databinding.TaskItemBinding
import com.example.todolist.domain.TaskItem

class TaskListAdapter :
    ListAdapter<TaskItem, TaskItemViewHolder>(TaskItemDiffCallBack()) {

    var onTaskItemLongClickListener: ((TaskItem) -> Unit)? = null
    var onTaskItemClickListener: ((TaskItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {

        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskItemViewHolder(binding.root)
    }
    override fun onBindViewHolder(viewHolder: TaskItemViewHolder, position: Int) {
        val taskItem = getItem(position)

        viewHolder.tvTaskItemName.text = taskItem.name
        viewHolder.tvTaskItemDateStart.text = taskItem.dateStart.toString()

//        viewHolder.view.setOnLongClickListener {
//            onPositionItemLongClickListener?.invoke(positionItem)
//            true
//        }
        viewHolder.view.setOnClickListener {
            onTaskItemClickListener?.invoke(taskItem)
        }
    }

    companion object {
        const val NOT_PURCHASED_TYPE = 100
        const val PURCHASED_TYPE = 101
    }
}