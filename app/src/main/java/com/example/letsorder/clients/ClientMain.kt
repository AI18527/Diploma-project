package com.example.letsorder.clients

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.letsorder.R

// for The QR if clients only have
class ClientMain: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)

        val menuButton: Button = findViewById(R.id.buttonOpenMenu)
        menuButton.setOnClickListener {
            Intent(this, ClientMenu::class.java).also {
                startActivity(it)
            }
        }
    }

}