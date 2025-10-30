package com.example.examenreproductor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.examenreproductor.view.CustomButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterButton: CustomButton = findViewById(R.id.enterButton)
        val exitButton: CustomButton = findViewById(R.id.exitButton)

        // El botón Entrar no hace nada por ahora
        enterButton.setOnClickListener {
            // No action
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}