package com.example.todolist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItemDbModel::class], version = 1)
abstract class AppDataBase :RoomDatabase() {
    abstract fun TaskItemDao():TaskItemDao

    companion object{
        private const val DB_NAME = "task_item_db"
        private var INSTANCE: AppDataBase? = null
        private val LOCK = Any()


        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}