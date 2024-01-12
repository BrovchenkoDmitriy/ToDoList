package com.example.todolist.domain

class GetTaskItemUseCase(private val repository: Repository) {
    suspend fun getTaskItem(id: Int): TaskItem {
        return repository.getTaskItem(id)
    }
}