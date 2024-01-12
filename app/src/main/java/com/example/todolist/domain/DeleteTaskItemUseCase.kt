package com.example.todolist.domain

class DeleteTaskItemUseCase(private val repository: Repository) {
    suspend fun deleteTaskItem(taskItem: TaskItem) {
        repository.deleteTaskItem(taskItem)
    }
}