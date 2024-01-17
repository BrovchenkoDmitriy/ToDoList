package com.example.todolist.presantation.taskItem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.RepositoryImpl
import com.example.todolist.domain.AddTaskItemUseCase
import com.example.todolist.domain.GetTaskItemUseCase
import com.example.todolist.domain.TaskItem
import com.example.todolist.domain.UpgradeTaskItemUseCase
import kotlinx.coroutines.launch

class TaskItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryImpl(application)
    private val upgradeTaskItemUseCase = UpgradeTaskItemUseCase(repository)
    private val addTaskItemUseCase = AddTaskItemUseCase(repository)
    private val getTaskItemUseCase = GetTaskItemUseCase(repository)

    private val  _startOrFinishTimeSetup = MutableLiveData<Int>()
    val startOrFinishTimeSetup: LiveData<Int> = _startOrFinishTimeSetup

    private val  _startTime = MutableLiveData<Long>()
    val startTime: LiveData<Long> = _startTime
    private val  _finishTime = MutableLiveData<Long>()
    val finishTime: LiveData<Long> = _finishTime

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _taskItem = MutableLiveData<TaskItem>()
    val taskItem: LiveData<TaskItem>
        get() = _taskItem

    private val _closeTaskItemScreen = MutableLiveData<Unit>()
    val closeTaskItemScreen: LiveData<Unit>
        get() = _closeTaskItemScreen

    fun getTaskItem(positionItemID: Int) {
        viewModelScope.launch {
            val item = getTaskItemUseCase.getTaskItem(positionItemID)
            _taskItem.value = item
        }


    }

    fun addTaskItem(name: String, dateStart: Long, dateFinish: Long, description: String) {
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            viewModelScope.launch {
                addTaskItemUseCase.addTaskItem(
                    TaskItem(
                        name = name,
                        dateStart = dateStart,
                        dateFinish = dateFinish,
                        description = description
                    )
                )
                finish()
            }
        }
    }

    fun upgradeTaskItem(name: String, dateStart: Long, dateFinish: Long, description: String) {
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            _taskItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        name = name,
                        dateStart = dateStart,
                        dateFinish = dateFinish,
                        description = description
                    )
                    upgradeTaskItemUseCase.upgradeTaskItem(item)
                    finish()
                }
            }
        }
    }

    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        return result
    }

    private fun finish() {
        _closeTaskItemScreen.value = Unit
    }

    fun resetInputNameError() {
        _errorInputName.value = false
    }

    fun setStartOrFinishTimeMode(value: Int){
        _startOrFinishTimeSetup.value = value
    }
    fun setStartTime(value:Long){
        _startTime.value = value
    }
    fun setFinishTime(value: Long){
        _finishTime.value = value
    }
}