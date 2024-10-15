package com.example.moneymanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "budget")
data class Budget(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "type_color") val typeColor: String,
    val amount: Double,
    @ColumnInfo(name = "type_money_id") val typeMoneyId: Int,
    @ColumnInfo(name = "period_date_start") val periodDateStart: Long,
    @ColumnInfo(name = "period_date_end") val periodDateEnd: Long,
    val period: String,
    @ColumnInfo(name = "icon_category") val iconCategory: String
)
