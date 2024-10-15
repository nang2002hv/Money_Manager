package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "initial_amount") val initialAmount: Double,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "type_wallet_id") val typeWalletId: Int,
    @ColumnInfo(name = "type_money_id") val typeMoneyId: Int
)