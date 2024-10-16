package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "deposit_and_withdraw", foreignKeys = [ForeignKey(
        entity = Goal::class,
        parentColumns = ["id"],
        childColumns = ["goal_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DepositAndWithdraw(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val type: String,
    @ColumnInfo(name = "goal_id") val goalId: Int
)