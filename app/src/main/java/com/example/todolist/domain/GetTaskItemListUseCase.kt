package com.example.todolist.domain

import androidx.lifecycle.LiveData

class GetTaskItemListUseCase(private val repository: Repository) {
    fun getTaskItemList(dateStart: Long, dateFinish: Long): LiveData<List<TaskItem>> {
        return repository.getTaskItemList(dateStart, dateFinish)
    }
}