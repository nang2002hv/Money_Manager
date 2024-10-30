package com.example.moneymanager.data.repository

import com.example.moneymanager.data.dao.TransferDao
import com.example.moneymanager.data.entity.Transfer
import javax.inject.Inject


interface TransferRepository {
    suspend fun insertTransfer(transfer : Transfer) : Long

}

class TransferRepositoryImpl @Inject constructor(
    private val transferDao: TransferDao
) : TransferRepository {
    override suspend fun insertTransfer(transfer: Transfer): Long {
        return transferDao.insertTransfer(transfer)
    }

}
