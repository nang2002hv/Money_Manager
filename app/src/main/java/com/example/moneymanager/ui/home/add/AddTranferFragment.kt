package com.example.moneymanager.ui.home.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentAddTranferBinding


class AddTranferFragment : Fragment() {

    private var _binding: FragmentAddTranferBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTranferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navGraph()
        back()

    }

    fun back(){
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    fun navGraph() {
        val controller_nav = findNavController()
        binding.tvAddExpense.setOnClickListener {
            controller_nav.navigate(R.id.addExpenseFragment)
        }
        binding.tvIncome.setOnClickListener {
            controller_nav.navigate(R.id.addIncomeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}