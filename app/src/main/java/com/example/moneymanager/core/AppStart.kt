package com.example.moneymanager.core

import android.content.Context
import android.content.SharedPreferences

//enum class AppStart {
//    FIRST_TIME, FIRST_TIME_VERSION, NORMAL
//}
//
//fun checkAppStart(context: Context): AppStart {
//    val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
//    val isFirstLaunch = sharedPreferences.getBoolean("IsFirstLaunch", true)
//
//    return if (isFirstLaunch) {
//        // If it's the first launch, update the flag in SharedPreferences
//        sharedPreferences.edit().putBoolean("IsFirstLaunch", false).apply()
//        AppStart.FIRST_TIME
//    } else {
//        AppStart.NORMAL
//    }
//}
//
enum class LanguageStart {
    INITIALIZED, NOT_INITIALIZED
}

fun checkLanguageInitialization(context: Context): LanguageStart {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val isLanguageInitialized = sharedPreferences.getBoolean("IsLanguageInitialized", false)

    return if (isLanguageInitialized) {
        LanguageStart.INITIALIZED
    } else {
        LanguageStart.NOT_INITIALIZED
    }
}
