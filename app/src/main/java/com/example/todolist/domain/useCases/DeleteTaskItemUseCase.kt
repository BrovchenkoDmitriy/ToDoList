package com.example.todolist.domain.useCases

import com.example.todolist.domain.Repository
import javax.inject.Inject

class DeleteTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun deleteTaskItem(taskItemId: Int) {
        repository.deleteTaskItem(taskItemId)
    }
}