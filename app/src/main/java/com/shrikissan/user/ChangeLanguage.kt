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
            binding.hindiName.setTextColor(ContextCompat.getColor(this, R.color.greenDark))
            binding.englishName.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.englishTick.visibility = View.GONE
            binding.hindiTick.visibility = View.VISIBLE
        }
        binding.english.setOnClickListener {
            binding.englishName.setTextColor(ContextCompat.getColor(this, R.color.greenDark))
            binding.hindiName.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.englishTick.visibility = View.VISIBLE
            binding.hindiTick.visibility = View.GONE
            setLanguage("en")
        }
        binding.hindi.setOnClickListener {
            binding.hindiName.setTextColor(ContextCompat.getColor(this, R.color.greenDark))
            binding.englishName.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.englishTick.visibility = View.GONE
            binding.hindiTick.visibility = View.VISIBLE
            setLanguage("hi")
        }
        binding.next.setOnClickListener {
           finish()
        }
    }
    fun setLanguage(code:String){
        val res =  resources
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = res.configuration
        config.setLocale(locale)
         createConfigurationContext(config)
        res.updateConfiguration(config,res.displayMetrics)
        data.edit {
            putString(Constants.language,code)
            apply()
        }
        setStrings()
    }
    private fun setStrings(){
        binding.textView3.setText(R.string.which_language_do_you_prefer)
    }
}