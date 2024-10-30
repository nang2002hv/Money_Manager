package com.example.moneymanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.moneymanager.data.entity.Transfer

@Dao
interface TransferDao {

    @Insert
    suspend fun insertTransfer(transfer: Transfer) : Long
}
