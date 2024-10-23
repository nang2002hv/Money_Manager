package com.example.moneymanager.di

import com.example.moneymanager.data.repository.AccountRepository
import com.example.moneymanager.data.repository.AccountRepositoryImpl
import com.example.moneymanager.data.repository.IncomeAndExpenseRepository
import com.example.moneymanager.data.repository.IncomeAndExpenseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindIncomeAndExpenseRepository(incomeAndExpenseRepositoryImpl: IncomeAndExpenseRepositoryImpl): IncomeAndExpenseRepository
}