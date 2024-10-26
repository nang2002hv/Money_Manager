package com.example.moneymanager.ui.home.home_screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moneymanager.data.entity.AccountWithWallet
import com.example.moneymanager.databinding.AccountSelectionBottomSheetBinding
import com.example.moneymanager.ui.home.home_screen.adapter.AccountAdapter

class AccountSelectorBottomSheet(
    private val accounts: List<AccountWithWallet>,
    private val currentAccount: AccountWithWallet,
    private val onAccountSelected: (AccountWithWallet) -> Unit,
    private val onAddAccount: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: AccountSelectionBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var accountAdapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AccountSelectionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountAdapter = AccountAdapter(accounts, currentAccount) { account ->
            onAccountSelected(account)
            dismiss()
        }

        binding.recyclerViewAccounts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountAdapter
        }

        binding.addAccount.setOnClickListener{
            onAddAccount()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}