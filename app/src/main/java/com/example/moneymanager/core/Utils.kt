package com.example.moneymanager.core

import java.util.Date

// Convert Long to Date
fun Long.toDate(): Date {
    return Date(this)
}

// Convert Date to Long
fun Date.toTimestamp(): Long {
    return this.time
}
