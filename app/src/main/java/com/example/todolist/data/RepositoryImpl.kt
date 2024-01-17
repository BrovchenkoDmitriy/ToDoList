package com.example.todolist.data

import android.app.Application
import com.example.todolist.data.mappers.TaskItemMapper
import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem

class RepositoryImpl(application: Application) : Repository {

    private val taskItemDao = AppDataBase.getInstance(application).TaskItemDao()
    private val taskItemMapper = TaskItemMapper()
    override suspend fun getTaskItem(taskItemId: Int): TaskItem {
        return taskItemMapper.mapDbModelToEntity(taskItemDao.getTaskItem(taskItemId))
    }

    override suspend fun addTaskItem(taskItem: TaskItem) {
        taskItemDao.addTaskItem(taskItemMapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun deleteTaskItem(taskItem: TaskItem) {
        taskItemDao.deleteTaskItem(taskItemMapper.mapEntityToDbModel(taskItem).id)
    }

    override suspend fun upgradeTaskItem(taskItem: TaskItem) {
        taskItemDao.addTaskItem(taskItemMapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun getTaskItemList(dateStart: Long, dateFinish: Long): List<TaskItem> {
        val taskItemDbModelList = taskItemDao.getTaskItemList(dateStart, dateFinish)
        return taskItemMapper.mapDbModelListToEntityList(taskItemDbModelList)
    }
}