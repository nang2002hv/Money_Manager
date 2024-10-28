package com.example.moneymanager.ui.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moneymanager.R
import com.example.moneymanager.data.entity.enums.WalletType
import com.example.moneymanager.databinding.FragmentHomeBinding
import com.example.moneymanager.ui.MainViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentAccount.collect {
                    it?.let {
                        val balance =
                            it.wallets.filter { wallet -> wallet.typeWallet == WalletType.GENERAL }
                                .sumOf { wallet -> wallet.amount }
                        val currencySymbol = getString(it.account.currency.symbolRes)
                        binding.balanceAmount.text =
                            getString(R.string.balance_amount, currencySymbol, balance)
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}