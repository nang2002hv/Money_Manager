package com.example.moneymanager.data.entity

data class AddIncomeAndExpense(
    val amount : Double,
    val description: String,
    val typeCategory : String,
    val typeOfExpenditure : String,
    val idWallet: Int,
    val linkImg: String,
    val transferDate: String,
    val transferTime: String
) {
    constructor() : this(0.0, "", "", "", 0, "", "", "")
}