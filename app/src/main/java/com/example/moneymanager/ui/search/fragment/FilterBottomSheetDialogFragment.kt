package com.example.moneymanager.ui.search.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moneymanager.databinding.FilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class FilterBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up date picker for start and end date
        binding.startDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.startDate.setText(selectedDate)
            }
        }

        binding.endDate.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.endDate.setText(selectedDate)
            }
        }

        // Apply filter button logic
        binding.applyFilterButton.setOnClickListener {
            applyFilter()
        }

        // Cancel button dismisses the dialog
        binding.cancelFilterButton.setOnClickListener {
            dismiss()
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDateSelected(selectedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun applyFilter() {
        // Collect filter inputs and apply logic
        val startDate = binding.startDate.text.toString()
        val endDate = binding.endDate.text.toString()
        val minAmount = binding.minAmount.text.toString().toDoubleOrNull()
        val maxAmount = binding.maxAmount.text.toString().toDoubleOrNull()

        // Add filter logic here...
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
