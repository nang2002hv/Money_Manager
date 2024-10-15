package com.example.moneymanager.core

import androidx.room.TypeConverter
import com.example.moneymanager.data.entity.WalletType

class WalletTypeConverter {

    @TypeConverter
    fun fromWalletType(walletType: WalletType): String {
        return walletType.name
    }

    @TypeConverter
    fun toWalletType(value: String): WalletType {
        return WalletType.valueOf(value)
    }
}
