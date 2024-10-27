package com.example.moneymanager.data.repository

import com.example.moneymanager.data.dao.WalletDao
import com.example.moneymanager.data.entity.Wallet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WalletRepository {
    fun getWalletsByUserId(userId: Long) : Flow<List<Wallet>>
    suspend fun insertWallet(wallet: Wallet) : Long
}

class WalletRepositoryImpl @Inject constructor(
    private val walletDao: WalletDao
) : WalletRepository {
    override fun getWalletsByUserId(userId: Long): Flow<List<Wallet>> {
        return walletDao.getWalletsByUserId(userId)
    }

    override suspend fun insertWallet(wallet: Wallet): Long {
        return walletDao.insertWallet(wallet)
    }
}