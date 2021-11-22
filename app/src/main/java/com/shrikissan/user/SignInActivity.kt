package com.shrikissan.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrikissan.user.databinding.ActivityMainBinding
import com.shrikissan.user.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}