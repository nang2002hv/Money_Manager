package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "target_date") val targetDate: Long,
    @ColumnInfo(name = "type_color") val typeColor: String,
    @ColumnInfo(name = "type_money_id") val typeMoneyId: Int,
    @ColumnInfo(name = "goal_amount") val goalAmount: Double,
    @ColumnInfo(name = "current_money") val currentMoney: Double
)