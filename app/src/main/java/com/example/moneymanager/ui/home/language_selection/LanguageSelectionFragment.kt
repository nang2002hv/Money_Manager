package com.example.moneymanager.ui.home.language_selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentLanguageSelectionBinding
import com.example.moneymanager.ui.MainViewModel

class LanguageSelectionFragment : Fragment() {

    private var _binding: FragmentLanguageSelectionBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var checkBoxes: List<CheckBox>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageSelectionBinding.inflate(inflater, container, false)

        checkBoxes = listOf(
            binding.englishCheckBox,
            binding.chineseCheckBox,
            binding.hindiCheckBox,
            binding.vietnameseCheckBox
        )

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkBoxes.filter { it != checkBox }.forEach { it.isChecked = false }
                }
                updateDoneButtonState()
            }
        }

        updateDoneButtonState()

        binding.btnDone.setOnClickListener {
            val selectedLanguage = checkBoxes.find { it.isChecked }?.tag.toString()
            mainViewModel.setLanguage(selectedLanguage)

            // Navigate to next screen and remove from backstack
            findNavController().navigate(R.id.action_languageSelectionFragment_to_addAccountFragment)
            findNavController().popBackStack(R.id.languageSelectionFragment, true)
        }

        return binding.root
    }

    private fun updateDoneButtonState() {
        binding.btnDone.isEnabled = checkBoxes.any { it.isChecked }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}