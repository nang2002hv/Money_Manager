package com.example.moneymanager.data.repository

import com.example.moneymanager.data.dao.IncomeAndExpenseDao
import com.example.moneymanager.data.entity.IncomeAndExpense
import javax.inject.Inject


interface IncomeAndExpenseRepository {
    suspend fun insertIncomeAndExpense(incomeAndExpense: IncomeAndExpense) : Long

}

class IncomeAndExpenseRepositoryImpl @Inject constructor(
    private val incomeAndExpenseDao: IncomeAndExpenseDao
) : IncomeAndExpenseRepository {
    override suspend fun insertIncomeAndExpense(incomeAndExpense: IncomeAndExpense): Long {
        return incomeAndExpenseDao.insertIncomeAndExpense(incomeAndExpense)
    }

}
