package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TaskItemDao {
    @Query("SELECT * FROM task_items WHERE dateStart>=:dataStart AND dateStart<=:dataFinish")
    fun getTaskItemList(dataStart:Long, dataFinish:Long): LiveData<List<TaskItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskItem(taskItemDbModel: TaskItemDbModel)

    @Query("DELETE FROM task_items WHERE id=:taskItemId")
    suspend fun deleteTaskItem(taskItemId: Int)

    @Query("SELECT*FROM task_items WHERE id=:taskItemId LIMIT 1")
    suspend fun getTaskItem(taskItemId: Int): TaskItemDbModel
}