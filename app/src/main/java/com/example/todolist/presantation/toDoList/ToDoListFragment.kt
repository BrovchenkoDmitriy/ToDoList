package com.example.todolist.presantation.toDoList

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.databinding.FragmentToDoListBinding
import com.example.todolist.presantation.toDoList.taskItemListRecycler.TaskListAdapter
import java.util.Calendar
import java.util.Locale


class ToDoListFragment : Fragment() {

    private val format = SimpleDateFormat("yyyy, dd MMMM HH:mm", Locale.getDefault())

    private lateinit var taskListAdapter: TaskListAdapter


    private var _binding: FragmentToDoListBinding? = null
    private val binding: FragmentToDoListBinding
        get() = _binding ?: throw RuntimeException("FragmentToDoListBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
        )[ToDoListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        setupRecyclerView()
        binding.calendarView.date = calendar.timeInMillis
        setText(calendar)
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth, 0, 0)
            calendarView.setDate(calendar.timeInMillis, true, true)
            setText(calendar)
        }
        binding.addNewTaskFloatingButton.setOnClickListener {
            findNavController().navigate(
                ToDoListFragmentDirections.actionToDoListFragmentToTaskItemFragment(MODE_ADD)
            )
        }

    }

    private fun setupRecyclerView() {
        with(binding.rvTaskItemList) {
            taskListAdapter = TaskListAdapter()
            adapter = taskListAdapter
            //устанавливаем максимальный пул для разных TypeView
        }
//        setupLongClickListener()
        setupClickListener()
//        setupSwipeListener(binding.rvTaskItemList)
    }

    private fun setupClickListener() {
        taskListAdapter.onTaskItemClickListener = {
            findNavController().navigate(
                ToDoListFragmentDirections.actionToDoListFragmentToTaskItemFragment(
                    MODE_EDIT, it.id
                )
            )
        }
    }

    private fun setText(calendar: Calendar) {
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            0,
            0,
            0
        )
        val timestampStartOFDay = (calendar.timeInMillis / 10000) * 10000

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            23,
            59,
            59
        )
        val timestampEndOFDay = (calendar.timeInMillis / 10000) * 10000
        Log.d("TAGIL", "query to database")
        Log.d("TAGIL", "start:$timestampStartOFDay  finish:$timestampEndOFDay")
        viewModel.getTaskItemList(timestampStartOFDay, timestampEndOFDay)
        viewModel.taskItemList.observe(viewLifecycleOwner) {
            taskListAdapter.submitList(it)
        }
        val resultStartOfDay = format.format(timestampStartOFDay) + " ($timestampStartOFDay)"
        val resultEndOfDay = format.format(timestampEndOFDay) + " ($timestampEndOFDay)"
        binding.timestampStart.text = resultStartOfDay
        binding.timestampEnd.text = resultEndOfDay
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
    }
}