package com.example.moneymanager.ui.main_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.data.entity.AccountWithWallet
import com.example.moneymanager.databinding.ItemAccountBinding

class AccountAdapter(
    private val accounts: List<AccountWithWallet>,
    private val currentAccount: AccountWithWallet,
    private val onAccountSelected: (AccountWithWallet) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holder.bind(account, account.account == currentAccount.account)
    }

    override fun getItemCount(): Int {
        return if (accounts.isNotEmpty()) accounts.size else 0
    }

    inner class AccountViewHolder(private val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: AccountWithWallet, isSelect: Boolean = false) {
            binding.accountName.text = account.account.nameAccount
            binding.accountBalance.text = account.wallets.sumOf { it.amount }.toString()
            binding.checkIcon.visibility = if (isSelect) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                onAccountSelected(account)
            }
        }
    }
}