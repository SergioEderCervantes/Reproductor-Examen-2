package com.example.examenreproductor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.examenreproductor.databinding.ActivityMainBinding
import com.example.examenreproductor.view.CustomButton

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val enterButton: CustomButton = b.enterButton
        val exitButton: CustomButton = b.exitButton

        enterButton.setOnClickListener {
            val intent = Intent(this, SelectorActivity::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}