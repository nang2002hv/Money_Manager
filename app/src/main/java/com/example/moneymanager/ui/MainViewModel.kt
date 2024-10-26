package com.example.moneymanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.entity.AccountWithWallet
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

    private val _accounts : MutableStateFlow<List<AccountWithWallet>> = MutableStateFlow(emptyList())
    val accounts : StateFlow<List<AccountWithWallet>> get() = _accounts

    private val _currentAccount : MutableStateFlow<AccountWithWallet?> = MutableStateFlow(null)
    val currentAccount : StateFlow<AccountWithWallet?> get() = _currentAccount

    private val _addingAccount = MutableStateFlow(AddingAccount("", Currency.USD, 0.0))
    val addingAccount: StateFlow<AddingAccount> get() = _addingAccount

//    init {
//        getAccount()
//        if(accounts.value.isNotEmpty()) {
//            setCurrentAccount(accounts.value[0])
//        }
//    }

    fun setAddingAccount(addingAccount: AddingAccount) {
        _addingAccount.value = addingAccount
    }

    fun setCurrentAccount(account: AccountWithWallet) {
        _currentAccount.value = account
    }

    fun getAccount() {
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
                    AccountWithWallet(
                        Account(
                            id = accountId,
                            nameAccount = newAccount.name,
                            currency = newAccount.currency
                        ),
                        emptyList()
                    )
                )

                _addingAccount.value = AddingAccount("", Currency.USD, 0.0)
            }
        }
    }

}