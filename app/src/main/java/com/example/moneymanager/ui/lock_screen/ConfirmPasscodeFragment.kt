package com.example.moneymanager.ui.lock_screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentConfirmPasscodeBinding
import com.example.moneymanager.ui.MainViewModel

class ConfirmPasscodeFragment : Fragment() {

    private lateinit var _binding: FragmentConfirmPasscodeBinding
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmPasscodeBinding.inflate(inflater, container, false)

        setupPasscodeInput()
        setupNumericKeyboard()
        setUpNumberActionButton()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setupNumericKeyboard() {
        val numericKeyboard = binding.numericKeyboard

        val buttons = listOf(
            numericKeyboard.btn0, numericKeyboard.btn1, numericKeyboard.btn2,
            numericKeyboard.btn3, numericKeyboard.btn4, numericKeyboard.btn5,
            numericKeyboard.btn6, numericKeyboard.btn7, numericKeyboard.btn8,
            numericKeyboard.btn9, numericKeyboard.deleteButton
        )

        buttons.forEach { button ->
            button.setOnClickListener { handleNumericButtonClick(button) }
        }
    }

    private fun handleNumericButtonClick(button: Button) {
        val passcodeFields = listOf(
            binding.passcodeField1,
            binding.passcodeField2,
            binding.passcodeField3,
            binding.passcodeField4,
            binding.passcodeField5,
            binding.passcodeField6
        )

        when (button.text.toString()) {
            "Delete" -> {
                // Delete the last character in the passcode field
            }
            else -> {
                for (field in passcodeFields) {
                    if (field.text.isEmpty()) {
                        field.setText(button.text.toString())
                        if (field != passcodeFields.last()) {
                            passcodeFields[passcodeFields.indexOf(field) + 1].requestFocus()
                        }
                        break
                    }
                }
            }
        }

        binding.numericKeyboard.deleteButton.setOnClickListener {
            for (field in passcodeFields.reversed()) {
                if (field.text.isNotEmpty()) {
                    field.text.clear()
                    break
                }
            }
        }
    }

    private fun setUpNumberActionButton() {
        binding.numericKeyboard.btnConfirm.setOnClickListener {
            if (mainViewModel.passcode.value == getEnteredPasscode()) {
                savePasscode()
                findNavController().navigate(R.id.action_confirmPasscodeFragment_to_addAccountFragment)
            } else {
                Toast.makeText(context, getString(R.string.passcode_does_not_match), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savePasscode() {
        val passcode = getEnteredPasscode()

        if (passcode.length == 6) {
            activity?.getSharedPreferences(PASSCODE_PREF, Context.MODE_PRIVATE)?.edit()
                ?.putString(PASSCODE_PREF_KEY, passcode)?.apply()
            Toast.makeText(context, getString(R.string.passcode_saved), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, getString(R.string.please_enter_6_digit_passcode), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPasscodeInput() {
        val passcodeFields = listOf(
            binding.passcodeField1,
            binding.passcodeField2,
            binding.passcodeField3,
            binding.passcodeField4,
            binding.passcodeField5,
            binding.passcodeField6
        )

        for (i in passcodeFields.indices) {
            passcodeFields[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && i < passcodeFields.size - 1) {
                        passcodeFields[i + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun getEnteredPasscode(): String {
        return listOf(
            binding.passcodeField1.text.toString(),
            binding.passcodeField2.text.toString(),
            binding.passcodeField3.text.toString(),
            binding.passcodeField4.text.toString(),
            binding.passcodeField5.text.toString(),
            binding.passcodeField6.text.toString()
        ).joinToString("")
    }

    companion object {
        private const val PASSCODE_PREF_KEY = "passcode"
        private const val PASSCODE_PREF = "passcode_prefs"
    }
}