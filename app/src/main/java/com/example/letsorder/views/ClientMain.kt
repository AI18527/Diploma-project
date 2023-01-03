package com.example.letsorder.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.letsorder.R
import com.example.letsorder.databinding.ActivityClientMainBinding
import com.example.letsorder.databinding.ActivityMainBinding

class ClientMain: AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("CREATE", "Client")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        val binding = ActivityClientMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_client_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }
}