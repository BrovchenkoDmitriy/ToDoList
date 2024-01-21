package com.example.todolist.presantation.toDoListFragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.ToDoListApp
import com.example.todolist.databinding.FragmentToDoListBinding
import com.example.todolist.presantation.ViewModelFactory
import com.example.todolist.presantation.toDoListFragment.customDailyCalendarViewGroup.TaskView
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class ToDoListFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as ToDoListApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(
            this, viewModelFactory
        )[ToDoListViewModel::class.java]
    }

    private val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    private var _binding: FragmentToDoListBinding? = null
    private val binding: FragmentToDoListBinding
        get() = _binding ?: throw RuntimeException("FragmentToDoListBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
        initDailyTaskItemList(calendar)
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            initDailyTaskItemList(calendar)
        }
        binding.addNewTaskFloatingButton.setOnClickListener {
            findNavController().navigate(
                ToDoListFragmentDirections.actionToDoListFragmentToTaskItemFragment(MODE_ADD)
            )
        }

    }

    private fun initDailyTaskItemList(calendar: Calendar) {
        val timestampStartOFDay = getTimestamp(calendar, 0, 0, 0)
        val timestampEndOFDay = getTimestamp(calendar, 23, 59, 59)
        viewModel.getTaskItemList(timestampStartOFDay, timestampEndOFDay)

        binding.timestampStart.text = format.format(timestampStartOFDay)
        viewModel.taskItemList.observe(viewLifecycleOwner) { taskItemList ->

            val countOfPlannedText =
                "${getString(R.string.CountOfPlannedTask)} ${taskItemList.size}"
            binding.timestampEnd.text = countOfPlannedText
            binding.dailyCalendarViewGroup.removeAllViews()
            for (item in taskItemList) {
                binding.dailyCalendarViewGroup.addTask(item)
            }
            for (view in binding.dailyCalendarViewGroup.children) {
                view.setOnClickListener {
                    if (it is TaskView) {
                        findNavController().navigate(
                            ToDoListFragmentDirections.actionToDoListFragmentToTaskItemFragment(
                                MODE_EDIT, it.taskItemId
                            )
                        )
                    }
                }
                view.setOnLongClickListener {
                    if (it is TaskView) {
                        val builder = AlertDialog.Builder(requireActivity())
                        builder
                            .setTitle("${it.taskName} ${it.taskTimeStart}")
                            .setMessage(R.string.DeleteThisTaskQuestion)
                            .setPositiveButton(R.string.acceptButtonText) { dialog, _ ->
                                viewModel.deleteTaskItem(it.taskItemId)
                                dialog.cancel()
                                initDailyTaskItemList(calendar)
                            }.setNegativeButton(R.string.cancelButtonText) { dialog, _ ->
                                dialog.cancel()
                            }
                        builder.create().show()
                    }
                    true
                }
            }
        }
    }

    private fun getTimestamp(calendar: Calendar, hour: Int, minute: Int, second: Int): Long {
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            hour,
            minute,
            second
        )
        return (calendar.timeInMillis / 10000) * 10000
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
    }
}