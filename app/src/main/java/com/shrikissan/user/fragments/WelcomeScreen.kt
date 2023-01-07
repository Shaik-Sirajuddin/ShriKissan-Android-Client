package com.shrikissan.user.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrikissan.user.MainActivity
import com.shrikissan.user.databinding.FragmentWelcomeScreenBinding

class WelcomeScreen : Fragment() {
    private lateinit var binding: FragmentWelcomeScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWelcomeScreenBinding.inflate(inflater)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }, 2000)
        return binding.root
    }
}