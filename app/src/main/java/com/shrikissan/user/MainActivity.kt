package com.shrikissan.user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shrikissan.user.databinding.ActivityMainBinding
import com.shrikissan.user.models.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
        val data = getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
        Constants.offlineToken = data.getString(Constants.token, "") ?: ""
        Log.e("token", Constants.offlineToken)
    }

}