package com.example.moneymanager.ui.main_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenViewModel : ViewModel() {
    private val _currentFragmentId = MutableStateFlow(0)
    val currentFragmentId : StateFlow<Int> get() = _currentFragmentId

    fun setCurrentFragmentId(id: Int) {
        _currentFragmentId.value = id
    }
}