package com.example.moneymanager.ui.calendar_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentCalendarBinding
import com.example.moneymanager.ui.MainViewModel
import java.util.Calendar


class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by activityViewModels()
    private val calendarView : CalendarView by lazy {
        binding.calendarView
    }
    private val event = HashMap<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
            override fun onClick(eventDay: CalendarDay) {
                val day = String.format("%02d", eventDay.calendar.get(Calendar.DAY_OF_MONTH))
                val month = String.format("%02d", eventDay.calendar.get(Calendar.MONTH) + 1)
                val year = eventDay.calendar.get(Calendar.YEAR).toString()
                val bundle = bundleOf(
                    "day" to day,
                    "month" to month,
                    "year" to year,
                )
                val controller = findNavController()
                controller.navigate(R.id.detailDayFragment, bundle)
            }
        })
        calendarView.setOnPreviousPageChangeListener(object : OnCalendarPageChangeListener {
            override fun onChange() {
                val month = String.format("%02d", calendarView.currentPageDate.get(Calendar.MONTH))
                val year = calendarView.currentPageDate.get(Calendar.YEAR)
                Toast.makeText(requireContext(), "Month: $month, Year: $year", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}