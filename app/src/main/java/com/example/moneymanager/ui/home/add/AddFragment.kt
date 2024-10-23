package com.example.moneymanager.ui.home.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentAddBinding
import com.example.moneymanager.ui.home.add.adapter.AddAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var addAdapter: AddAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        addAdapter = AddAdapter(this)
        viewPager.adapter = addAdapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.income)
                1 -> getString(R.string.add_expense)
                2 -> getString(R.string.transfer)
                else -> null
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}