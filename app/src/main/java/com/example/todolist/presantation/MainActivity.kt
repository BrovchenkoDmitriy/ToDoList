package com.example.todolist.presantation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.presantation.taskItemFragment.TaskItemFragment

class MainActivity : AppCompatActivity(), TaskItemFragment.OnEditingIsFinishedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onEditingIsFinishedListener() {
        onBackPressedDispatcher.onBackPressed()
    }
}