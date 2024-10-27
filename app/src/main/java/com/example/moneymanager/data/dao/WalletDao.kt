package com.example.moneymanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneymanager.data.entity.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallet) : Long

    @Query("SELECT * FROM wallet where account_id = :userId")
    fun getWalletsByUserId(userId: Long) : Flow<List<Wallet>>

}