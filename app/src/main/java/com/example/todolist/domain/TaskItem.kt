package com.example.todolist.domain

data class TaskItem(
    val id: Int = UNDEFINED_ID,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}