package com.example.todolist.domain

import javax.inject.Inject

class GetTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getTaskItem(id: Int): TaskItem {
        return repository.getTaskItem(id)
    }
}