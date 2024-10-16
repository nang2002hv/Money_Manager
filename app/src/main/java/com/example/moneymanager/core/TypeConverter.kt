package com.example.moneymanager.core

import androidx.room.TypeConverter
import com.example.moneymanager.data.entity.enums.Currency
import com.example.moneymanager.data.entity.enums.WalletType

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

class CurrencyTypeConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): Int {
        return currency.id
    }

    @TypeConverter
    fun toCurrency(id: Int): Currency? {
        return Currency.fromId(id)
    }
}

