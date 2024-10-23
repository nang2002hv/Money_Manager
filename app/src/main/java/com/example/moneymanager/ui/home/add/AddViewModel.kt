package com.example.moneymanager.ui.home.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.data.entity.IncomeAndExpense
import com.example.moneymanager.data.repository.IncomeAndExpenseRepository
import com.example.moneymanager.di.AppDispatchers
import com.example.moneymanager.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.sql.Date
import java.sql.Time
import java.util.Locale
import javax.inject.Inject

data class AddingIncomeAndExpense(
    val amount : Double,
    val description: String,
    val typeCategory : String,
    val typeOfExpenditure : String,
    val idWallet: Int,
    val linkImg: String,
    val transferDate: Date,
    val transferTime: Time

)

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: IncomeAndExpenseRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
)  : ViewModel() {

    private val _incomeAndExpense = MutableStateFlow(AddingIncomeAndExpense(0.0, "", "", "", 0, "", Date(0), Time(0)))
    val incomeAndExpense: StateFlow<AddingIncomeAndExpense> get() = _incomeAndExpense

    private val _selectedDate = MutableStateFlow<String>("")
    val selectedDate: StateFlow<String> get() = _selectedDate

    private val _selectedTime = MutableStateFlow<String>("")
    val selectedTime: StateFlow<String> get() = _selectedTime

    private val _currentDateTime = MutableStateFlow<Pair<String, String>>(Pair("", ""))
    val currentDateTime: StateFlow<Pair<String, String>> get() = _currentDateTime

    private val _imageUri = MutableStateFlow<Bitmap?>(null)
    val imageUri: StateFlow<Bitmap?> get() = _imageUri


    fun setBitmap(bitmap: Bitmap) {
        Log.d("nang", "setBitmap() called with: bitmap = $bitmap")
        _imageUri.value = bitmap
    }

    fun showDatePickerDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            _selectedDate.value = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    fun showTimePickerDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
            val selectedTime = "$selectedHour:$selectedMinute"
            _selectedTime.value = selectedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }

    fun updateDateTime() {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        _currentDateTime.value = Pair(dateFormat.format(currentDate), timeFormat.format(currentDate))
    }

    fun insertIncomeAndExpense() {
        viewModelScope.launch(ioDispatcher) {
            val newIncomeAndExpense = incomeAndExpense.value
            if (newIncomeAndExpense.amount > 0) {
                repository.insertIncomeAndExpense(
                    IncomeAndExpense(
                        amount = newIncomeAndExpense.amount,
                        description = newIncomeAndExpense.description,
                        typeCategory = newIncomeAndExpense.typeCategory,
                        typeOfExpenditure = newIncomeAndExpense.typeOfExpenditure,
                        walletId = newIncomeAndExpense.idWallet,
                        linkImg = newIncomeAndExpense.linkImg,
                        transferDate = 0,
                        transferTime = 0
                    )
                )
            }
        }
    }

}