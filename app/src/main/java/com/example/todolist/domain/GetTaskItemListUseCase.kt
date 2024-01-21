package com.example.todolist.domain

import javax.inject.Inject

class GetTaskItemListUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getTaskItemList(dateStart: Long, dateFinish: Long): List<TaskItem> {
        return repository.getTaskItemList(dateStart, dateFinish)
    }
}