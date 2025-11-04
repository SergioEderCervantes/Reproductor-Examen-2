package com.example.examenreproductor

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.examenreproductor.databinding.ActivityMainBinding
import com.example.examenreproductor.view.CustomButton
import kotlin.math.hypot

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val enterButton: CustomButton = b.enterButton
        val exitButton: CustomButton = b.exitButton

        enterButton.setOnClickListener {
            val revealView = b.revealView

            val cx = (enterButton.left + enterButton.right) / 2
            val cy = (enterButton.top + enterButton.bottom) / 2

            val finalRadius = hypot(revealView.width.toDouble(), revealView.height.toDouble()).toFloat()

            val anim = ViewAnimationUtils.createCircularReveal(revealView, cx, cy, 0f, finalRadius)
            revealView.visibility = View.VISIBLE
            anim.duration = 500

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(this@MainActivity, SelectorActivity::class.java)
                    startActivity(intent)
                }
            })

            anim.start()
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
    override fun onResume() {
        super.onResume()
        if (b.revealView.visibility == View.VISIBLE) {
            val enterButton = b.enterButton
            val revealView = b.revealView

            val cx = (enterButton.x + enterButton.width / 2).toInt()
            val cy = (enterButton.y + enterButton.height / 2).toInt()

            val initialRadius = hypot(revealView.width.toDouble(), revealView.height.toDouble()).toFloat()

            val anim = ViewAnimationUtils.createCircularReveal(revealView, cx, cy, initialRadius, 0f)
            anim.duration = 500 // Adjust duration as needed

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    revealView.visibility = View.INVISIBLE
                }
            })
            anim.start()
        }
    }
}