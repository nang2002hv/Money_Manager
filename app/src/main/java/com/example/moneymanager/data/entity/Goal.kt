package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moneymanager.core.CurrencyTypeConverter
import com.example.moneymanager.data.entity.enums.Currency

@Entity(
    tableName = "goal", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Goal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "target_date") val targetDate: Long,
    @ColumnInfo(name = "type_color") val typeColor: String,
    @ColumnInfo(name = "goal_amount") val goalAmount: Double,
    @ColumnInfo(name = "current_money") val currentMoney: Double
)