package com.example.moneymanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.entity.AccountWithWallet
import com.example.moneymanager.data.entity.Wallet
import com.example.moneymanager.data.entity.enums.Currency
import com.example.moneymanager.data.entity.enums.WalletType
import com.example.moneymanager.data.repository.AccountRepository
import com.example.moneymanager.data.repository.WalletRepository
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
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val accountRepository: AccountRepository,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _accounts : MutableStateFlow<List<AccountWithWallet>> = MutableStateFlow(emptyList())
    val accounts : StateFlow<List<AccountWithWallet>> get() = _accounts

    private val _currentAccount : MutableStateFlow<AccountWithWallet?> = MutableStateFlow(null)
    val currentAccount : StateFlow<AccountWithWallet?> get() = _currentAccount

    private val _addingAccount = MutableStateFlow(AddingAccount("", Currency.USD, 0.0))
    val addingAccount: StateFlow<AddingAccount> get() = _addingAccount

    private val _passcode = MutableStateFlow("")
    val passcode: StateFlow<String> get() = _passcode

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
            accountRepository.getAccount().collect {
                _accounts.value = it
            }
        }
    }

    fun addAccount() {
        viewModelScope.launch(ioDispatcher) {
            val newAccount = addingAccount.value
            if (newAccount.name.isNotEmpty()) {
                val accountId = accountRepository.insertAccount(
                    Account(
                        nameAccount = newAccount.name,
                        currency = newAccount.currency
                    )
                )
                val walletId = walletRepository.insertWallet(
                    Wallet(
                        accountId = accountId,
                        amount = newAccount.initAmount,
                        typeWallet = WalletType.GENERAL
                    )
                )

                setCurrentAccount(
                    AccountWithWallet(
                        Account(
                            id = accountId,
                            nameAccount = newAccount.name,
                            currency = newAccount.currency
                        ),
                        listOf(
                            Wallet(
                                id = walletId,
                                accountId = accountId,
                                amount = newAccount.initAmount,
                                typeWallet = WalletType.GENERAL
                            )
                        )
                    )
                )

                _addingAccount.value = AddingAccount("", Currency.USD, 0.0)
            }
        }
    }

    fun setPasscode(passcode: String) {
        _passcode.value = passcode
    }

}