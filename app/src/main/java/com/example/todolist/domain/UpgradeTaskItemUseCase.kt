package com.example.todolist.domain

class UpgradeTaskItemUseCase(private val repository: Repository) {
    suspend fun upgradeTaskItem(taskItem: TaskItem) {
        repository.upgradeTaskItem(taskItem)
    }
}