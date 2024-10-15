package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfer_wallet")
data class TransferWallet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val description: String,
    @ColumnInfo(name = "from_wallet") val fromWallet: Int,
    @ColumnInfo(name = "to_wallet")val toWallet: Int,
    val fee: Double,
    @ColumnInfo(name = "link_img") val linkImg: String,
    @ColumnInfo(name = "transfer_date") val transferDate: Long
)