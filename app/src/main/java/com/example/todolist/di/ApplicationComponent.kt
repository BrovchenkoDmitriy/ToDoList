package com.example.todolist.di

import android.app.Application
import com.example.todolist.presantation.taskItem.TaskItemFragment
import com.example.todolist.presantation.toDoList.ToDoListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: ToDoListFragment)
    fun inject(fragment: TaskItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}