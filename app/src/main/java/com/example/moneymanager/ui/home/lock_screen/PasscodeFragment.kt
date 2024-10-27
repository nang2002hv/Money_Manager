package com.example.moneymanager.ui.home.lock_screen

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
import com.example.moneymanager.databinding.FragmentPasscodeBinding
import com.example.moneymanager.ui.MainViewModel

class PasscodeFragment : Fragment() {

    private var _binding: FragmentPasscodeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasscodeBinding.inflate(inflater, container, false)

        setupPasscodeInput()
        setupNumericKeyboard()
        setUpNumberActionButton()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backButton.visibility = View.GONE

        return binding.root
    }

    private fun setupNumericKeyboard() {
        // Accessing the numeric keyboard buttons using the 'tag' attribute
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
                // Clear the last field that has a value
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
            for (i in passcodeFields.size - 1 downTo 0) {
                if (passcodeFields[i].text.isNotEmpty()) {
                    passcodeFields[i].text.clear()
                    break
                }
            }
        }
    }

    private fun setUpNumberActionButton() {
        binding.numericKeyboard.btnConfirm.setOnClickListener {
            if (checkIfPasscodeExists()) {
                checkPasscode().let {
                    if (it) {
                        findNavController().navigate(R.id.action_lockScreenFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, getString(R.string.incorrect_pass_code), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                mainViewModel.setPasscode(getEnteredPasscode())
                findNavController().navigate(R.id.action_lockScreenFragment_to_confirmPasscodeFragment)
            }
        }
    }

    private fun checkPasscode(): Boolean {
        val enteredPasscode = getEnteredPasscode()
        val savedPasscode = activity?.getSharedPreferences("passcode_prefs", Context.MODE_PRIVATE)
            ?.getString(PASSCODE_PREF_KEY, null)

        return enteredPasscode == savedPasscode
    }

    private fun checkIfPasscodeExists(): Boolean {
        val sharedPreferences = activity?.getSharedPreferences("passcode_prefs", Context.MODE_PRIVATE)
        return sharedPreferences?.contains(PASSCODE_PREF_KEY) == true
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PASSCODE_PREF_KEY = "passcode"
    }
}
