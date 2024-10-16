package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moneymanager.core.CurrencyTypeConverter
import com.example.moneymanager.data.entity.enums.Currency

@Entity(
    tableName = "budget", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Budget(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "account_id") val accountId: Int,
    @ColumnInfo(name = "type_color") val typeColor: String,
    val amount: Double,
    @TypeConverters(CurrencyTypeConverter::class)
    @ColumnInfo(name = "currency") val currency: Currency,
    @ColumnInfo(name = "period_date_start") val periodDateStart: Long,
    @ColumnInfo(name = "period_date_end") val periodDateEnd: Long,
    val period: String,
    @ColumnInfo(name = "icon_category") val iconCategory: String
)
