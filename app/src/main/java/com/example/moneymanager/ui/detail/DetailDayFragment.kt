package com.example.moneymanager.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentDetailDayBinding


class DetailDayFragment : Fragment() {

    private var _binding: FragmentDetailDayBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        ShowDay(bundle!!)
    }

     fun ShowDay(bundle: Bundle){
        val day = bundle.getString("day")
        val month = bundle.getString("month")
        val year = bundle.getString("year")
        Toast.makeText(requireContext(), "Day: $day, Month: $month, Year: $year", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}