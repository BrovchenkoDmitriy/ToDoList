package com.example.todolist.testUtils

import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.junit4.MockKRule
import org.junit.Rule

class ViewModelTest {
    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)
}