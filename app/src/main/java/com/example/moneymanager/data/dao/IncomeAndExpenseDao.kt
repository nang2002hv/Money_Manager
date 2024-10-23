package com.example.moneymanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.moneymanager.data.entity.IncomeAndExpense

@Dao
interface IncomeAndExpenseDao {
    @Insert
    suspend fun insertIncomeAndExpense(incomeAndExpense: IncomeAndExpense) : Long
}
