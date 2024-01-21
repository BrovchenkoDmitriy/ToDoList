package com.example.todolist.di

import android.app.Application
import com.example.todolist.data.AppDataBase
import com.example.todolist.data.RepositoryImpl
import com.example.todolist.data.TaskItemDao
import com.example.todolist.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): TaskItemDao {
            return AppDataBase.getInstance(application).TaskItemDao()
        }
    }

}