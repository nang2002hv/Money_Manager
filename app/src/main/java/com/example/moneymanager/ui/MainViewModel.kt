package com.example.moneymanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.entity.enums.Currency
import com.example.moneymanager.data.repository.AccountRepository
import com.example.moneymanager.di.AppDispatchers
import com.example.moneymanager.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddingAccount(
    val name: String,
    val currency: Currency,
    val initAmount: Double,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AccountRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _accounts : MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    val accounts : StateFlow<List<Account>> get() = _accounts

    private val _currentAccount : MutableStateFlow<Account?> = MutableStateFlow(null)
    val currentAccount : StateFlow<Account?> get() = _currentAccount

    private val _selectedLanguage = MutableStateFlow("en") // Default language code
    val selectedLanguage: StateFlow<String> get() = _selectedLanguage

    private val _addingAccount = MutableStateFlow(AddingAccount("", Currency.USD, 0.0))
    val addingAccount: StateFlow<AddingAccount> get() = _addingAccount

    init {
        getAccount()
        if(accounts.value.isNotEmpty()) {
            setCurrentAccount(accounts.value[0])
        }
    }

    fun setLanguage(languageCode: String) {
        _selectedLanguage.value = languageCode
    }

    fun setAddingAccount(addingAccount: AddingAccount) {
        _addingAccount.value = addingAccount
    }

    fun setCurrentAccount(account: Account) {
        _currentAccount.value = account
    }

    private fun getAccount() {
        viewModelScope.launch {
            repository.getAccount().collect {
                _accounts.value = it
            }
        }
    }

    fun addAccount() {
        viewModelScope.launch(ioDispatcher) {
            val newAccount = addingAccount.value
            if (newAccount.name.isNotEmpty()) {
                val accountId = repository.insertAccount(
                    Account(
                        nameAccount = newAccount.name,
                        currency = newAccount.currency
                    )
                )

                setCurrentAccount(
                    Account(
                        id = accountId,
                        nameAccount = newAccount.name,
                        currency = newAccount.currency
                    )
                )

                _addingAccount.value = AddingAccount("", Currency.USD, 0.0)
            }
        }
    }

}