package com.example.todolist.domain

class GetTaskItemListUseCase(private val repository: Repository) {
    suspend fun getTaskItemList(dateStart: Long, dateFinish: Long): List<TaskItem> {
        return repository.getTaskItemList(dateStart, dateFinish)
    }
}