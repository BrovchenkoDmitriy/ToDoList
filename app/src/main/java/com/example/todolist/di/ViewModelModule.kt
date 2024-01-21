package com.example.todolist.di

import androidx.lifecycle.ViewModel
import com.example.todolist.presantation.taskItemFragment.TaskItemViewModel
import com.example.todolist.presantation.toDoListFragment.ToDoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ToDoListViewModel::class)
    fun bindToDoListViewModel(viewModel: ToDoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskItemViewModel::class)
    fun bindTaskItemViewModel(viewModel: TaskItemViewModel):ViewModel

}