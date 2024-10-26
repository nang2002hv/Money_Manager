package com.example.moneymanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moneymanager.data.entity.Account
import com.example.moneymanager.data.entity.AccountWithWallet
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAccount() : Flow<List<AccountWithWallet>>

    @Insert
    suspend fun insertAccount(account: Account) : Long
}