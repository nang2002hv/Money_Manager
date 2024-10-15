package com.example.moneymanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.entity.Budget
import com.example.moneymanager.data.entity.Debt
import com.example.moneymanager.data.entity.DepositAndWithdraw
import com.example.moneymanager.data.entity.Goal
import com.example.moneymanager.data.entity.IncomeAndExpense
import com.example.moneymanager.data.entity.TransferWallet
import com.example.moneymanager.data.entity.TypeMoney
import com.example.moneymanager.data.entity.TypeWallet
import com.example.moneymanager.data.entity.Wallet

@Database(
    entities = [
        Account::class, Budget::class, Debt::class, DepositAndWithdraw::class, Goal::class,
        IncomeAndExpense::class, TransferWallet::class, TypeMoney::class, TypeWallet::class, Wallet::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase()