package com.example.todolist.presantation.toDoList.taskItemListRecycler

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.domain.TaskItem

class TaskItemDiffCallBack : DiffUtil.ItemCallback<TaskItem>() {
    override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
        return oldItem == newItem
    }

}