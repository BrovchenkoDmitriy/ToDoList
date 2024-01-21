package com.example.todolist.domain.useCases

import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem
import javax.inject.Inject

class UpgradeTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun upgradeTaskItem(taskItem: TaskItem) {
        repository.upgradeTaskItem(taskItem)
    }
}