package com.example.moneymanager.ui.home.add.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moneymanager.ui.home.add.AddExpenseFragment
import com.example.moneymanager.ui.home.add.AddIncomeFragment
import com.example.moneymanager.ui.home.add.AddTranferFragment

class AddAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AddIncomeFragment()
            1 -> AddExpenseFragment()
            2 -> AddTranferFragment()
            else -> throw IllegalStateException("Invalid Fragment")
        }
    }
}