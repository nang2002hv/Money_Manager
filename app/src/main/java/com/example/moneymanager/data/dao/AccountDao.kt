package com.example.moneymanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moneymanager.data.entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAccount() : Flow<List<Account>>

    @Insert
    suspend fun insertAccount(account: Account) : Long
}