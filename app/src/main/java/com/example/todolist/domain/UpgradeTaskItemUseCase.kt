package com.example.todolist.domain

import javax.inject.Inject

class UpgradeTaskItemUseCase @Inject constructor(private val repository: Repository) {
    suspend fun upgradeTaskItem(taskItem: TaskItem) {
        repository.upgradeTaskItem(taskItem)
    }
}