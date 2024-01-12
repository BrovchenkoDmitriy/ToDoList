package com.example.todolist.data.mappers

import com.example.todolist.data.TaskItemDbModel
import com.example.todolist.domain.TaskItem

class TaskItemMapper {
    fun mapEntityToDbModel(taskItem: TaskItem): TaskItemDbModel {
        return TaskItemDbModel(
            id = taskItem.id,
            dateStart = taskItem.dateStart,
            dateFinish = taskItem.dateFinish,
            name = taskItem.name,
            description = taskItem.description,

            )
    }

    fun mapDbModelToEntity(taskItemDbModel: TaskItemDbModel): TaskItem {
        return TaskItem(
            id = taskItemDbModel.id,
            dateStart = taskItemDbModel.dateStart,
            dateFinish = taskItemDbModel.dateFinish,
            name = taskItemDbModel.name,
            description = taskItemDbModel.description,
        )
    }

    fun mapDbModelListToEntityList(list: List<TaskItemDbModel>): List<TaskItem> {
        return list.map {
            mapDbModelToEntity(it)
        }
    }
}