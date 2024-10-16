package com.example.moneymanager.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moneymanager.R
import com.example.moneymanager.core.AppStart
import com.example.moneymanager.core.checkAppStart
import com.example.moneymanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedLanguage.collect { languageCode ->
                    setAppLocale(languageCode)
                }
            }
        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        initAppStart(this)
    }

    private fun initAppStart(context: Context) {
        when (checkAppStart(context)) {
            AppStart.FIRST_TIME -> {
                navController.navInflater.inflate(R.navigation.nav_graph).setStartDestination(R.id.languageSelectionFragment)
            }

            AppStart.NORMAL -> {
                navController.navInflater.inflate(R.navigation.nav_graph).setStartDestination(R.id.lockScreenFragment)
            }
            AppStart.FIRST_TIME_VERSION -> {}
        }
    }

    private fun setAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        applyOverrideConfiguration(config)
    }
}