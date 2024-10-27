package com.example.moneymanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneymanager.data.dao.AccountDao
import com.example.moneymanager.data.dao.IncomeAndExpenseDao
import com.example.moneymanager.data.dao.WalletDao
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.entity.Budget
import com.example.moneymanager.data.entity.Debt
import com.example.moneymanager.data.entity.DepositAndWithdraw
import com.example.moneymanager.data.entity.Goal
import com.example.moneymanager.data.entity.IncomeAndExpense
import com.example.moneymanager.data.entity.TransferWallet
import com.example.moneymanager.data.entity.Wallet

@Database(
    entities = [
        Account::class, Budget::class, Debt::class, DepositAndWithdraw::class, Goal::class,
        IncomeAndExpense::class, TransferWallet::class, Wallet::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun accountDao(): AccountDao
//    abstract fun budgetDao(): BudgetDao
//    abstract fun debtDao(): DebtDao
//    abstract fun depositAndWithdrawDao(): DepositAndWithdrawDao
//    abstract fun goalDao(): GoalDao
    abstract fun incomeAndExpenseDao(): IncomeAndExpenseDao
//    abstract fun transferWalletDao(): TransferWalletDao
//    abstract fun typeMoneyDao(): TypeMoneyDao
    abstract fun walletDao(): WalletDao
}