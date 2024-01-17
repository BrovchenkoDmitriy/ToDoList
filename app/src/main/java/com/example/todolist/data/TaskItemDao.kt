package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskItemDao {
    @Query("SELECT * FROM task_items WHERE dateStart BETWEEN :dataStart AND :dataFinish")
    suspend fun getTaskItemList(dataStart: Long, dataFinish: Long): List<TaskItemDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskItem(taskItemDbModel: TaskItemDbModel)

    @Query("DELETE FROM task_items WHERE id=:taskItemId")
    suspend fun deleteTaskItem(taskItemId: Int)

    @Query("SELECT*FROM task_items WHERE id=:taskItemId LIMIT 1")
    suspend fun getTaskItem(taskItemId: Int): TaskItemDbModel
}