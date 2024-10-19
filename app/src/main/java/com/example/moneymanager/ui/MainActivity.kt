package com.example.moneymanager.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moneymanager.R
import com.example.moneymanager.core.LanguageStart
import com.example.moneymanager.core.checkLanguageInitialization
import com.example.moneymanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check language initialization
        if (checkLanguageInitialization(this) == LanguageStart.NOT_INITIALIZED) {
            setPreferredLocale("en") // Set default language to English
        }

        setContentView(binding.root)

        // Initialize NavController
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setDynamicStartDestination()
    }

    private fun setDynamicStartDestination() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val languageStart = checkLanguageInitialization(this@MainActivity)
                Log.d(TAG, "Checking language start: $languageStart")

                val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
                val startDestination = when (languageStart) {
                    LanguageStart.NOT_INITIALIZED -> R.id.languageSelectionFragment
                    LanguageStart.INITIALIZED -> {
                        if (viewModel.accounts.value.isNotEmpty()) {
                            R.id.homeFragment
                        } else {
                            R.id.addAccountFragment
                        }
                    }
                }

                navGraph.setStartDestination(startDestination)
                navController.graph = navGraph // Apply the new graph to NavController
            }
        }
    }

    private fun setPreferredLocale(languageCode: String) {
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
