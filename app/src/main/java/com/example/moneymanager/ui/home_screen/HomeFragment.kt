package com.example.moneymanager.ui.home_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.data.entity.AccountWithWallet
import com.example.moneymanager.databinding.FragmentHomeBinding
import com.example.moneymanager.ui.MainViewModel
import com.example.moneymanager.ui.home_screen.fragment.AccountSelectorBottomSheet
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentAccount.collect {
                    it?.let {
                        binding.profileName.text = it.account.nameAccount
                        val balance = it.wallets.sumOf { wallet -> wallet.amount }
                        val currencySymbol = getString(it.account.currency.symbolRes)
                        binding.balanceAmount.text =
                            getString(R.string.balance_amount, currencySymbol, balance)
                    }
                }
            }
        }

        binding.profileName.setOnClickListener {
            mainViewModel.accounts.value.let { accounts ->
                mainViewModel.currentAccount.value?.let { currentAccount ->
                    openAccountSelector(accounts, currentAccount)
                }
            }
        }

        binding.dropdownIcon.setOnClickListener {
            mainViewModel.accounts.value.let { accounts ->
                mainViewModel.currentAccount.value?.let { currentAccount ->
                    openAccountSelector(accounts, currentAccount)
                }
            }
        }

        return binding.root
    }

    private fun openAccountSelector(
        accounts: List<AccountWithWallet>, currentAccount: AccountWithWallet
    ) {
        val accountSelector = AccountSelectorBottomSheet(accounts,
            currentAccount,
            { account -> selectAccount(account) },
            { addAccount() })

        accountSelector.show(parentFragmentManager, "AccountSelectorBottomSheet")
    }

    private fun selectAccount(account: AccountWithWallet) {
        mainViewModel.setCurrentAccount(account)
    }

    private fun addAccount() {
        // Add account
        findNavController().navigate(R.id.action_mainFragment_to_addAccountFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}