package com.example.letsorder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.letsorder.adminpanel.AdminMain
import com.example.letsorder.clients.ClientMain
import com.example.letsorder.waiters.WaiterMain
import kotlinx.coroutines.NonCancellable.start


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clientButton: Button = findViewById(R.id.buttonClient)
        clientButton.setOnClickListener {
            Intent(this, ClientMain::class.java).also {
                startActivity(it)
            }
        }

        val waiterButton: Button = findViewById(R.id.buttonWaiter)
        waiterButton.setOnClickListener{
            Intent(this, WaiterMain::class.java).also{
                startActivity(it)
            }
        }

        val adminButton: Button = findViewById(R.id.buttonAdmin)
        adminButton.setOnClickListener{
            Intent(this, AdminMain::class.java).also{
                startActivity(it)
            }
        }
    }
}