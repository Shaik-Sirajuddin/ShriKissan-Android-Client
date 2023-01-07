package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView

import android.view.animation.Animation
import android.view.animation.AnimationUtils


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        val logo = findViewById<ImageView>(R.id.logo)
        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_anim)

        logo.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}