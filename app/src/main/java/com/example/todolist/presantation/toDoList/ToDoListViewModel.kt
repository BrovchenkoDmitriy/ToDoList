package com.example.todolist.presantation.toDoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.DeleteTaskItemUseCase
import com.example.todolist.domain.GetTaskItemListUseCase
import com.example.todolist.domain.TaskItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ToDoListViewModel @Inject constructor(
    private val getTaskItemListUseCase: GetTaskItemListUseCase,
    private val deleteTaskItemUseCase: DeleteTaskItemUseCase
) : ViewModel() {


    private val _taskItemList = MutableLiveData<List<TaskItem>>()
    val taskItemList: LiveData<List<TaskItem>> = _taskItemList

    fun getTaskItemList(timeStart: Long, timeFinish: Long) {
        viewModelScope.launch {
            val a = getTaskItemListUseCase.getTaskItemList(timeStart, timeFinish)
            _taskItemList.value = a
        }
    }

    fun deleteTaskItem(taskItemId: Int) {
        viewModelScope.launch {
            deleteTaskItemUseCase.deleteTaskItem(taskItemId)
        }
    }
}