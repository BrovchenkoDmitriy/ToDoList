package com.example.todolist.presentation.toDoListFragment

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import com.example.todolist.domain.TaskItem
import com.example.todolist.domain.useCases.GetTaskItemListUseCase
import com.example.todolist.presantation.toDoListFragment.ToDoListViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class ToDoListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = TestCoroutineDispatcher()

    @After
    fun afterEach() {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
    }

    @Before
    fun beforeEach() {
        Dispatchers.setMain(dispatcher)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

        })

    }

    @Test
    fun `should get TaskItem list`() {
        val testTaskItemList = mutableListOf<TaskItem>()
        val expectedList = mutableListOf<TaskItem>()
        for (i in 1..5) {

            val taskItem = TaskItem(
                i, 1705840000, 1709440000, "name$i", "description"
            )
            testTaskItemList.add(taskItem)
            expectedList.add(taskItem)
        }
        for (i in 6..10) {
            testTaskItemList.add(
                TaskItem(
                    i, 1705830000, 1709440000, "name$i", "description"
                )
            )
        }

        val _taskItemList = MutableLiveData<List<TaskItem>>()
        val timeStart = 1705839999L
            val timeFinish = 1709440001L
        val viewModelTest = mockk<ToDoListViewModel>(relaxed = true) {
            every { taskItemList } returns _taskItemList
        }
        CoroutineScope(Dispatchers.Main).launch {
            mockk<GetTaskItemListUseCase>() {
                _taskItemList.value = testTaskItemList.filter { it.dateStart> timeStart && timeStart<timeFinish}
            }
        }
        viewModelTest.getTaskItemList(timeStart, timeFinish)
        _taskItemList.value = viewModelTest.taskItemList.value

        val expected = expectedList
        val actual = viewModelTest.taskItemList.value

        println(expected.toString())
        println(actual.toString())

        assert(expected == actual)
    }
}