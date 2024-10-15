package com.example.moneymanager.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moneymanager.core.WalletTypeConverter

@Entity(tableName = "type_wallet")
data class TypeWallet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @TypeConverters(WalletTypeConverter::class)
    @ColumnInfo(name = "name_type_wallet") val nameTypeWallet: WalletType
)

enum class WalletType {
    GENERAL, CREDIT_CARD
}
