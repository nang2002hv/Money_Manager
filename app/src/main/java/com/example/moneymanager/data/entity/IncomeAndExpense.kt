package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "income_and_expense",

)
data class IncomeAndExpense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val description: String,
    @ColumnInfo(name = "type_category") val typeCategory: String,
    @ColumnInfo(name = "type_of_expenditure") val typeOfExpenditure: String,
    @ColumnInfo(name = "wallet_id") val walletId: Int,
    @ColumnInfo(name = "link_img") val linkImg: String,
    @ColumnInfo(name = "transfer_date") val transferDate: Long,
    @ColumnInfo(name = "transfer_time") val transferTime: Long
)