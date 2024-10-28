package com.example.moneymanager.ui.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentMainBinding
import com.example.moneymanager.ui.main_screen.adapter.MainPagerAdapter

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainScreenViewModel by viewModels()
    private lateinit var vpAdapter: MainPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        vpAdapter = MainPagerAdapter(this)
        binding.mainViewPager.adapter = vpAdapter // Make sure to set the adapter

        // Synchronize BottomNavigationView with ViewPager2
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> binding.mainViewPager.currentItem = 0
                R.id.navigation_calendar -> binding.mainViewPager.currentItem = 1
                R.id.navigation_statistic -> binding.mainViewPager.currentItem = 2
                R.id.navigation_wallet -> binding.mainViewPager.currentItem = 3
            }
            true
        }

        // Synchronize ViewPager2 with BottomNavigationView
        binding.mainViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.setCurrentFragmentId(position)
                when (position) {
                    0 -> binding.bottomNavigation.selectedItemId = R.id.navigation_home
                    1 -> binding.bottomNavigation.selectedItemId = R.id.navigation_calendar
                    2 -> binding.bottomNavigation.selectedItemId = R.id.navigation_statistic
                    3 -> binding.bottomNavigation.selectedItemId = R.id.navigation_wallet
                }
            }
        })

        binding.addFab.setOnClickListener {
            when (viewModel.currentFragmentId.value) {
                0 -> {
                    // Action for Home Fragment
                    findNavController().navigate(R.id.addExpenseFragment)
                }

                1 -> {
                    // Action for Calendar Fragment
//                    findNavController().navigate(R.id.addCalendarEventFragment)
                }

                2 -> {
                    // Action for Statistic Fragment
//                    findNavController().navigate(R.id.addStatisticFragment)
                }

                3 -> {
                    // Action for Wallet Fragment
//                    findNavController().navigate(R.id.addWalletFragment)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
