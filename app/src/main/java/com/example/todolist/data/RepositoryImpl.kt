package com.example.todolist.data

import com.example.todolist.data.mappers.TaskItemMapper
import com.example.todolist.domain.Repository
import com.example.todolist.domain.TaskItem
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val taskItemMapper: TaskItemMapper,
    private val taskItemDao: TaskItemDao
) : Repository {

    override suspend fun getTaskItem(taskItemId: Int): TaskItem {
        return taskItemMapper.mapDbModelToEntity(taskItemDao.getTaskItem(taskItemId))
    }

    override suspend fun addTaskItem(taskItem: TaskItem) {
        taskItemDao.addTaskItem(taskItemMapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun deleteTaskItem(taskItemId: Int) {
        taskItemDao.deleteTaskItem(taskItemId)
    }

    override suspend fun upgradeTaskItem(taskItem: TaskItem) {
        taskItemDao.addTaskItem(taskItemMapper.mapEntityToDbModel(taskItem))
    }

    override suspend fun getTaskItemList(dateStart: Long, dateFinish: Long): List<TaskItem> {
        val taskItemDbModelList = taskItemDao.getTaskItemList(dateStart, dateFinish)
        return taskItemMapper.mapDbModelListToEntityList(taskItemDbModelList)
    }
}