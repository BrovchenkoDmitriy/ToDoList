package com.example.todolist.domain

import androidx.lifecycle.LiveData

interface Repository {
    suspend fun getTaskItem(taskItemId: Int):TaskItem
    suspend fun addTaskItem(taskItem: TaskItem)
    suspend fun deleteTaskItem(taskItem: TaskItem)
    suspend fun upgradeTaskItem(taskItem: TaskItem)
    fun getTaskItemList(dateStart:Long, dateFinish:Long): LiveData<List<TaskItem>>
}