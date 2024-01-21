package com.example.todolist

import android.app.Application
import com.example.todolist.di.DaggerApplicationComponent

class ToDoListApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}