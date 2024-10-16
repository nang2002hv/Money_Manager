package com.example.moneymanager.data.entity.enums

import androidx.annotation.StringRes
import com.example.moneymanager.R

enum class Currency(
    val id: Int, @StringRes val nameRes: Int, @StringRes val symbolRes: Int
) {
    USD(1, R.string.currency_usd, R.string.currency_usd_symbol),
    INR(2, R.string.currency_inr, R.string.currency_inr_symbol),
    VND(3, R.string.currency_vnd, R.string.currency_vnd_symbol),
    CNY(4, R.string.currency_cny, R.string.currency_cny_symbol);

    companion object {
        // Function to retrieve a Currency by its ID
        fun fromId(id: Int): Currency? = entries.find { it.id == id }
    }
}
