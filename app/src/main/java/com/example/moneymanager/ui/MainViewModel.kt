package com.example.moneymanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AccountRepository,
) : ViewModel() {

    private val _accounts : MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts : StateFlow<List<Account>> get() = _accounts

    private val _currentAccount : MutableStateFlow<Account?> = MutableStateFlow(null)
    val currentAccount : StateFlow<Account?> get() = _currentAccount

    init {
        getAccount()
    }

    private fun getAccount() {
        viewModelScope.launch {
            repository.getAccount().collect {
                _accounts.value = it
            }
        }
    }

    fun setCurrentAccount(account: Account) {
        _currentAccount.value = account
    }
}