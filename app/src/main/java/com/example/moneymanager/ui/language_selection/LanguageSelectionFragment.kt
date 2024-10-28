package com.example.moneymanager.ui.language_selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.moneymanager.R
import com.example.moneymanager.databinding.FragmentLanguageSelectionBinding
import com.example.moneymanager.ui.MainActivity

class LanguageSelectionFragment : Fragment() {

    private var _binding: FragmentLanguageSelectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var checkBoxes: List<CheckBox>

    // Supported language codes
    private val supportedLanguages = mapOf(
        "en" to "English",
        "zh" to "Chinese",
        "hi" to "Hindi",
        "vi" to "Vietnamese"
    )

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

        // Pre-select the current language when the fragment is loaded
        preselectCurrentLanguage()

        // Set up listeners for checkboxes
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
            val selectedLanguageCode = checkBoxes.find { it.isChecked }?.tag.toString()
            if (supportedLanguages.containsKey(selectedLanguageCode)) {
                // Set language and update shared preferences
                setPreferredLocale(selectedLanguageCode)
                updateLanguageInitialized(requireContext())

                // Restart the main activity
                restartApp()
            }
        }

        return binding.root
    }

    // Pre-select the current language based on the system locale
    private fun preselectCurrentLanguage() {
        val currentLocale = AppCompatDelegate.getApplicationLocales()[0]?.language
        checkBoxes.forEach { checkBox ->
            if (checkBox.tag == currentLocale) {
                checkBox.isChecked = true
            }
        }
    }

    private fun updateDoneButtonState() {
        binding.btnDone.isEnabled = checkBoxes.any { it.isChecked }
    }

    private fun setPreferredLocale(languageCode: String) {
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    private fun updateLanguageInitialized(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("IsLanguageInitialized", true).apply()
    }

    // Restart the main activity
    private fun restartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        requireActivity().finish()
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
