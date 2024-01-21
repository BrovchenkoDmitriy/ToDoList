package com.example.todolist.domain.useCases

import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem
import javax.inject.Inject

class GetTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getTaskItem(id: Int): TaskItem {
        return repository.getTaskItem(id)
    }
}