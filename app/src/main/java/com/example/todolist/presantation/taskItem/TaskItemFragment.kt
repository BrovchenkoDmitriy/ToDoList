package com.example.todolist.presantation.taskItem

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.todolist.databinding.DateTimePickerBottomsheetDialogBinding
import com.example.todolist.databinding.FragmentTaskItemBinding
import com.example.todolist.domain.TaskItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

class TaskItemFragment : Fragment() {

    private lateinit var dateTimePickerBottomsheetDialog: BottomSheetDialog

    private var start by Delegates.notNull<Long>()
    private var finish by Delegates.notNull<Long>()

    private val format = SimpleDateFormat("EE, dd MMMM yyyy, HH:mm  >", Locale.getDefault())

    private var screenMode = SCREEN_MODE_UNDEFINED
    private var taskItemId: Int = TaskItem.UNDEFINED_ID

    private var _binding: FragmentTaskItemBinding? = null
    private val binding: FragmentTaskItemBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskItemBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
        )[TaskItemViewModel::class.java]
    }
    private val args: TaskItemFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startTime.observe(viewLifecycleOwner) {
            start = it
        }
        viewModel.finishTime.observe(viewLifecycleOwner) {
            finish = it
        }
        chooseRightMode()
        setupDateTimePickerBottomSheetDialog()
        binding.tvStartTaskTime.setOnClickListener {
            dateTimePickerBottomsheetDialog.show()
            viewModel.setStartOrFinishTimeMode(START_TIME_MODE)
        }
        binding.tvEndTaskTime.setOnClickListener {
            dateTimePickerBottomsheetDialog.show()
            viewModel.setStartOrFinishTimeMode(FINISH_TIME_MODE)
        }
        binding.etTaskDescription.setHorizontallyScrolling(false)
        binding.etTaskDescription.maxLines = FIVE_LINES
        binding.etTaskDescription.autoSizeMaxTextSize
    }

    private fun setupDateTimePickerBottomSheetDialog() {

        val dialogView = DateTimePickerBottomsheetDialogBinding.inflate(layoutInflater)
        dialogView.timePicker.setIs24HourView(true)
        dateTimePickerBottomsheetDialog = BottomSheetDialog(requireContext())
        dateTimePickerBottomsheetDialog.setContentView(dialogView.root)
        dateTimePickerBottomsheetDialog.behavior.maxHeight =
            resources.displayMetrics.heightPixels / 2

        viewModel.startOrFinishTimeSetup.observe(viewLifecycleOwner) {
            val timeMode = it
            val calendar = Calendar.getInstance()

            dialogView.datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
            }
            dialogView.timePicker.setOnTimeChangedListener { _, hoursOfDay, minute ->
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    hoursOfDay,
                    minute
                )
            }

            if (timeMode == DATE_START) {
                calendar.timeInMillis = start
                dialogView.datePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialogView.timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
                dialogView.timePicker.minute = calendar.get(Calendar.MINUTE)
                dialogView.acceptButton.setOnClickListener {
                    viewModel.setStartTime(calendar.timeInMillis)
                    viewModel.setFinishTime(calendar.timeInMillis + HOUR_IN_MILLIS)
                    binding.tvStartTaskTime.text = format.format(start)
                    binding.tvEndTaskTime.text = format.format(finish)
                    dateTimePickerBottomsheetDialog.hide()
                }
            } else {
                calendar.timeInMillis = finish
                dialogView.datePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dialogView.timePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
                dialogView.timePicker.minute = calendar.get(Calendar.MINUTE)
                dialogView.acceptButton.setOnClickListener {
                    if (calendar.timeInMillis > start)
                        viewModel.setFinishTime(calendar.timeInMillis)
                    else viewModel.setFinishTime(start)
                    binding.tvStartTaskTime.text = format.format(start)
                    binding.tvEndTaskTime.text = format.format(finish)
                    dateTimePickerBottomsheetDialog.hide()
                }
            }
            dialogView.cancelButton.setOnClickListener {
                dateTimePickerBottomsheetDialog.hide()
            }
        }
    }

    private fun chooseRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getTaskItem(taskItemId)
        viewModel.taskItem.observe(viewLifecycleOwner) {
            val taskItem = it
            viewModel.setStartTime(taskItem.dateStart)
            viewModel.setFinishTime(taskItem.dateFinish)
            with(binding) {
                etTaskName.setText(taskItem.name)
                tvStartTaskTime.text = format.format(taskItem.dateStart)
                tvEndTaskTime.text = format.format(taskItem.dateFinish)
                etTaskDescription.setText(taskItem.description)
            }
        }

        binding.confirmButton.setOnClickListener {
            val name = binding.etTaskName.text?.toString() ?: ""
            val description = binding.etTaskDescription.text?.toString() ?: ""
            viewModel.upgradeTaskItem(
                name,
                start,
                finish,
                description
            )
        }
    }

    private fun launchAddMode() {
        val calendar = Calendar.getInstance()
        val dateStart = calendar.timeInMillis
        val dateFinish = calendar.timeInMillis + HOUR_IN_MILLIS
        viewModel.setStartTime(dateStart)
        viewModel.setFinishTime(dateFinish)
        binding.tvStartTaskTime.text = format.format(dateStart)
        binding.tvEndTaskTime.text = format.format(dateFinish)
        binding.confirmButton.setOnClickListener {
            val name = binding.etTaskName.text?.toString() ?: ""
            val description = binding.etTaskDescription.text?.toString() ?: ""
            viewModel.addTaskItem(
                name,
                start,
                finish,
                description
            )
        }
    }

    private fun parseParams() {
        val args = args
        val mode = args.extraMode
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is wrong")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            taskItemId = args.taskItemId
        }
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE_UNDEFINED = ""
        private const val DATE_START = 1
        private const val HOUR_IN_MILLIS = 3600000
        private const val START_TIME_MODE = 1
        private const val FINISH_TIME_MODE = 2
        private const val FIVE_LINES = 5
    }
}