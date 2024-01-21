package com.example.todolist.domain.useCases

import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem
import javax.inject.Inject

class AddTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun addTaskItem(taskItem: TaskItem) {
        repository.addTaskItem(taskItem)
    }
}