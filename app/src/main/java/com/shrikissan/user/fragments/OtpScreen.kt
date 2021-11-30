package com.shrikissan.user.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.shrikissan.user.*
import com.shrikissan.user.models.Constants
import com.shrikissan.user.databinding.FragmentOtpScreenBinding
import com.shrikissan.user.models.showToast
import java.util.*
import android.os.Build

import android.annotation.TargetApi
import android.content.res.Configuration
import android.content.res.Resources


class OtpScreen : Fragment() {
    private lateinit var binding: FragmentOtpScreenBinding
    private lateinit var data: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOtpScreenBinding.inflate(inflater)
        data = requireContext().getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
        binding.english.setOnClickListener {
            binding.englishName.setTextColor(ContextCompat.getColor(requireContext(), R.color.greenDark))
            binding.hindiName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.englishTick.visibility = View.VISIBLE
            binding.hindiTick.visibility = View.GONE
            setLanguage("en")
        }
        binding.hindi.setOnClickListener {
            binding.hindiName.setTextColor(ContextCompat.getColor(requireContext(), R.color.greenDark))
            binding.englishName.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.englishTick.visibility = View.GONE
            binding.hindiTick.visibility = View.VISIBLE
            setLanguage("hi")
        }
        binding.next.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }
        return binding.root
    }
    private fun setLanguage(code:String){
        val res =  requireContext().resources
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = res.configuration
        config.setLocale(locale)
        requireContext().createConfigurationContext(config)
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