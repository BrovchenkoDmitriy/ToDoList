package com.example.todolist.presantation.toDoList

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.FragmentToDoListBinding
import java.util.Calendar
import java.util.Locale


class ToDoListFragment : Fragment() {

    private val format = SimpleDateFormat("yyyy, dd MMMM HH:mm", Locale.getDefault())

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
        binding.calendarView.date = calendar.timeInMillis
        setText(calendar)
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth, 0, 0)
            calendarView.setDate(calendar.timeInMillis, true, true)
            setText(calendar)
        }
    }
    private fun setText(calendar: Calendar){
        val timestamp = (calendar.timeInMillis / 10000) * 10000
        calendar.set(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            23,
            59,59)
        val timestampEndOFDay = (calendar.timeInMillis / 10000) * 10000
        val resultStartOfDay = format.format(timestamp) + " ($timestamp)"
        val resultEndOfDay = format.format(timestampEndOFDay) + " ($timestampEndOFDay)"
        binding.timestampStart.text = resultStartOfDay
        binding.timestampEnd.text = resultEndOfDay
    }
}