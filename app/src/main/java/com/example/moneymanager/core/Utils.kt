package com.example.moneymanager.core

import android.content.Context
import com.example.moneymanager.data.entity.enums.Currency
import java.util.Date

// Convert Long to Date
fun Long.toDate(): Date {
    return Date(this)
}

// Convert Date to Long
fun Date.toTimestamp(): Long {
    return this.time
}

fun getCurrencyName(context: Context, currency: Currency): String {
    return context.getString(currency.nameRes)
}

fun getCurrencySymbol(context: Context, currency: Currency): String {
    return context.getString(currency.symbolRes)
}