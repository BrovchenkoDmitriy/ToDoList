package com.example.todolist.presantation.toDoList.taskItemListRecycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class TaskItemViewHolder(val view: View) :
    RecyclerView.ViewHolder(view) {

    val tvTaskItemName: TextView = view.findViewById(R.id.tv_task_item_name)
    val tvTaskItemDateStart: TextView = view.findViewById(R.id.tv_task_item_date_start)
}