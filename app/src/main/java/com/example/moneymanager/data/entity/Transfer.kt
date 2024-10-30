package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transfer" , foreignKeys = [
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["from_wallet"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["to_wallet"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transfer(
    @PrimaryKey(autoGenerate = true) val  id : Long = 0,
    @ColumnInfo(name = "from_wallet") val fromWallet: Long,
    @ColumnInfo(name = "to_wallet") val toWallet: Long,
    val amount: Double,
    val fee: Double,
    val description: String,
    @ColumnInfo(name = "link_img") val linkImg: String,
    @ColumnInfo(name = "transfer_date") val transferDate: Long,
    @ColumnInfo(name = "transfer_time") val transferTime: Long,
    @ColumnInfo(name = "type_of_expenditure") val typeOfExpenditure: String,
    @ColumnInfo(name = "type_debt") val typeDebt: String,
    @ColumnInfo(name = "type_icon_category") val typeIconCategory: String,
    @ColumnInfo(name = "type_color") val typeColor: String,
    @ColumnInfo(name = "type_icon_wallet") val typeIconWallet: String

)
