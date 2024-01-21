package com.example.todolist.domain

import javax.inject.Inject

class AddTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun addTaskItem(taskItem: TaskItem) {
        repository.addTaskItem(taskItem)
    }
}