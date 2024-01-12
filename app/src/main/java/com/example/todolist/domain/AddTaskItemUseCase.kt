package com.example.todolist.domain

class AddTaskItemUseCase(private val repository: Repository) {
    suspend fun addTaskItem(taskItem: TaskItem) {
        repository.addTaskItem(taskItem)
    }
}