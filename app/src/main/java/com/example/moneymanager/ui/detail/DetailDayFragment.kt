package com.example.moneymanager.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moneymanager.R
import com.example.moneymanager.core.CurrencyTypeConverter
import com.example.moneymanager.databinding.FragmentDetailDayBinding
import java.util.Calendar

class DetailDayFragment : Fragment() {

    private var _binding: FragmentDetailDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        ShowDay(bundle!!)
    }

    fun ShowDay(bundle: Bundle) {
        val day = bundle.getString("day")
        val month = bundle.getString("month")
        val year = bundle.getString("year")
        Toast.makeText(requireContext(), "Day: $day, Month: $month, Year: $year", Toast.LENGTH_SHORT).show()
        binding.tvDate.text = day.toString()
        val calendar = Calendar.getInstance()
        calendar.set(year!!.toInt(), month!!.toInt() - 1, day!!.toInt()) // Trừ 1 cho tháng
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        binding.tvDayOfWeek.text = CurrencyTypeConverter().getDayOfWeekString(dayOfWeek)
        binding.tvMonthYear.text = "th $month $year"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}