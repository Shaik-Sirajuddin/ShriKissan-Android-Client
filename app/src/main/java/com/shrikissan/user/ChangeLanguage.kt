package com.shrikissan.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.shrikissan.user.databinding.ActivityChangeLanguageBinding
import com.shrikissan.user.models.Constants
import java.util.*

class ChangeLanguage : AppCompatActivity() {
    private lateinit var binding:ActivityChangeLanguageBinding
    private lateinit var data: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
        val lang = data.getString(Constants.language,"en")?:"en"
        if(lang=="hi"){
            binding.hindiLayout.setBackgroundResource(R.color.redlight)
            binding.englishLayout.setBackgroundResource(R.color.white)
            setLanguage("hi")
        }
        else{
            binding.hindiLayout.setBackgroundResource(R.color.white)
            binding.englishLayout.setBackgroundResource(R.color.redlight)
            setLanguage("en")
        }
        binding.english.setOnClickListener {
            binding.hindiLayout.setBackgroundResource(R.color.white)
            binding.englishLayout.setBackgroundResource(R.color.redlight)
            setLanguage("en")
        }
        binding.hindi.setOnClickListener {
            binding.hindiLayout.setBackgroundResource(R.color.redlight)
            binding.englishLayout.setBackgroundResource(R.color.white)
            setLanguage("hi")
        }
        binding.next.setOnClickListener {
           finish()
        }
    }
    private fun setLanguage(code:String){
        val res =  resources
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = res.configuration
        config.setLocale(locale)
         createConfigurationContext(config)
        res.updateConfiguration(config,res.displayMetrics)
        data.edit {
            putString(Constants.language,code)
            commit()
        }
        setStrings()
    }
    private fun setStrings(){
        binding.textView3.setText(R.string.which_language_do_you_prefer)
    }
}