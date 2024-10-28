package com.example.moneymanager.ui.main_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneymanager.ui.calendar_screen.CalendarFragment
import com.example.moneymanager.ui.home_screen.HomeFragment
import com.example.moneymanager.ui.statistic_screen.StatisticFragment
import com.example.moneymanager.ui.wallet_screen.WalletFragment

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3  // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> CalendarFragment()
            2 -> StatisticFragment()
            3 -> WalletFragment()
            else -> HomeFragment()
        }
    }
}
