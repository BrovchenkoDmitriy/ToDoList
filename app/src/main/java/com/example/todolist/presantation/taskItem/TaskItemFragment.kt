package com.example.todolist.presantation.taskItem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.DateTimePickerBottomsheetDialogBinding
import com.example.todolist.databinding.FragmentTaskItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class TaskItemFragment : Fragment() {

    private lateinit var dateTimePickerBottomsheetDialog: BottomSheetDialog

    private var _binding: FragmentTaskItemBinding? = null
    private val binding: FragmentTaskItemBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskItemBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
        )[TaskItemViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setupDateTimePickerBottomSheetDialog()
        binding.tvStartTaskTime.setOnClickListener {
            dateTimePickerBottomsheetDialog.show()
        }
        binding.tvEndTaskTime.setOnClickListener {
            dateTimePickerBottomsheetDialog.show()
        }
        binding.etTaskDescription.setHorizontallyScrolling(false)
        binding.etTaskDescription.maxLines = 5
        binding.etTaskDescription.autoSizeMaxTextSize
    }

    private fun setupDateTimePickerBottomSheetDialog() {

        val dialogView = DateTimePickerBottomsheetDialogBinding.inflate(layoutInflater)
        dialogView.timePicker.setIs24HourView(true)
        dateTimePickerBottomsheetDialog = BottomSheetDialog(requireContext())
        dateTimePickerBottomsheetDialog.setContentView(dialogView.root)
        dateTimePickerBottomsheetDialog.behavior.maxHeight =
            resources.displayMetrics.heightPixels / 2
        dialogView.cancelButton.setOnClickListener { dateTimePickerBottomsheetDialog.hide() }
        dialogView.acceptButton.setOnClickListener { dateTimePickerBottomsheetDialog.hide() }
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            TaskItemFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}