package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moneymanager.core.WalletTypeConverter
import com.example.moneymanager.data.entity.enums.WalletType

@Entity(
    tableName = "wallet", foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @TypeConverters(WalletTypeConverter::class)
    @ColumnInfo(name = "type_wallet_name") val typeWallet: WalletType,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "icon_id") val iconId: String,
    @ColumnInfo(name = "color_id") val colorId: String
)