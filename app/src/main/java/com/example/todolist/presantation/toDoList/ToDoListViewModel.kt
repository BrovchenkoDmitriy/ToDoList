package com.example.todolist.presantation.toDoList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.RepositoryImpl
import com.example.todolist.domain.DeleteTaskItemUseCase
import com.example.todolist.domain.GetTaskItemListUseCase
import com.example.todolist.domain.TaskItem
import kotlinx.coroutines.launch

class ToDoListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryImpl(application)

    private val getTaskItemListUseCase = GetTaskItemListUseCase(repository)
    private val deleteTaskItemUseCase = DeleteTaskItemUseCase(repository)

    private val _taskItemList = MutableLiveData<List<TaskItem>>()
    val taskItemList: LiveData<List<TaskItem>> = _taskItemList

    fun getTaskItemList(timeStart: Long, timeFinish: Long) {
        viewModelScope.launch {
            val a = getTaskItemListUseCase.getTaskItemList(timeStart, timeFinish)
            _taskItemList.value = a
        }
    }

    fun deleteTaskItem(taskItem: TaskItem) {
        viewModelScope.launch {
            deleteTaskItemUseCase.deleteTaskItem(taskItem)
        }
    }
}