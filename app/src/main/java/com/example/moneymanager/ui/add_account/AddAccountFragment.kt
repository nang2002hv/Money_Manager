package com.example.moneymanager.ui.add_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentAddAccountBinding
import com.example.moneymanager.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAccountFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentAddAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddAccountBinding.inflate(inflater, container, false)

        binding.let { b ->
            b.etAccountName.addTextChangedListener { text ->
                b.btnNext.isEnabled = !text.isNullOrBlank()
            }
            b.btnNext.setOnClickListener {
                mainViewModel.setAddingAccount(mainViewModel.addingAccount.value.copy(name = b.etAccountName.text.toString()))
                findNavController().navigate(R.id.action_addAccountFragment_to_currencySelectionFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}