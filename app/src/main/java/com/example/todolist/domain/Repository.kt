package com.example.todolist.domain

interface Repository {
    suspend fun getTaskItem(taskItemId: Int): TaskItem
    suspend fun addTaskItem(taskItem: TaskItem)
    suspend fun deleteTaskItem(taskItemId: Int)
    suspend fun upgradeTaskItem(taskItem: TaskItem)
    suspend fun getTaskItemList(dateStart: Long, dateFinish: Long): List<TaskItem>
}