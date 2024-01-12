package com.example.todolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todolist.data.mappers.TaskItemMapper
import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem

class RepositoryImpl(application: Application) : Repository {

    private val taskItemDao = AppDataBase.getInstance(application).TaskItemDao()
    private val taskItemMapper = TaskItemMapper()
    override suspend fun getTaskItem(taskItemId: Int): TaskItem {
        TODO("Not yet implemented")
    }

    override suspend fun addTaskItem(taskItem: TaskItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskItem(taskItem: TaskItem) {
        TODO("Not yet implemented")
    }

    override suspend fun upgradeTaskItem(taskItem: TaskItem) {
        TODO("Not yet implemented")
    }

    override fun getTaskItemList(dateStart: Long, dateFinish: Long): LiveData<List<TaskItem>> {
        TODO("Not yet implemented")
    }
}