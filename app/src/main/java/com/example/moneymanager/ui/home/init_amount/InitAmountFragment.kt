package com.example.moneymanager.ui.home.init_amount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentInitAmountBinding
import com.example.moneymanager.ui.MainViewModel

class InitAmountFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentInitAmountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInitAmountBinding.inflate(inflater, container, false)

        val currentCurrency = mainViewModel.addingAccount.value.currency
        val currencySymbol = getString(currentCurrency.symbolRes)
        binding.etAmount.hint = getString(R.string.amount_hint, currencySymbol)
        binding.apply {
            tvSkip.setOnClickListener {
                // Skip the init amount
                handleAmountConfirmation(0.0)
            }
            btnConfirm.setOnClickListener {
                // Save the init amount
                val initAmount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
                handleAmountConfirmation(initAmount)
            }
        }

        return binding.root
    }

    private fun handleAmountConfirmation(amount: Double){
        mainViewModel.setAddingAccount(
            mainViewModel.addingAccount.value.copy(initAmount = amount)
        )
        mainViewModel.addAccount()
        val navOptions = navOptions {
            popUpTo(R.id.addAccountFragment) { inclusive = true }
        }
        findNavController().navigate(R.id.action_initAmountFragment_to_homeFragment, null, navOptions)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}