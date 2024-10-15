package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt")
data class Debt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    val amount: Double,
    @ColumnInfo(name = "type_color") val typeColor: String,
    @ColumnInfo(name = "type_money_id") val typeMoneyId: Int
)
