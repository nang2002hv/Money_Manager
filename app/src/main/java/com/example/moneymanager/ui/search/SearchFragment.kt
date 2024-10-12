package com.example.moneymanager.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moneymanager.databinding.FragmentSearchBinding
import com.example.moneymanager.ui.search.fragment.FilterBottomSheetDialogFragment

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Handle filter button click
        binding.filterButton.setOnClickListener {
            showFilterBottomSheet()
        }

        return binding.root
    }

    // Show the FilterBottomSheetDialogFragment
    private fun showFilterBottomSheet() {
        val filterBottomSheet = FilterBottomSheetDialogFragment()
        filterBottomSheet.show(parentFragmentManager, "FilterBottomSheetDialogFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
