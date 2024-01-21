package com.example.todolist.domain.useCases

import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem
import javax.inject.Inject

class GetTaskItemListUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getTaskItemList(dateStart: Long, dateFinish: Long): List<TaskItem> {
        return repository.getTaskItemList(dateStart, dateFinish)
    }
}