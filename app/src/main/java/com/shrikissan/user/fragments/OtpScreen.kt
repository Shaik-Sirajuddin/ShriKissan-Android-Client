package com.shrikissan.user.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrikissan.user.MainActivity
import com.shrikissan.user.models.Constants
import com.shrikissan.user.OTPReceiver
import com.shrikissan.user.R
import com.shrikissan.user.SignInActivity
import com.shrikissan.user.databinding.FragmentOtpScreenBinding


class OtpScreen : Fragment() {
    private lateinit var binding: FragmentOtpScreenBinding
    private lateinit var number:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOtpScreenBinding.inflate(inflater)
        binding.otpVw.requestFocus()
        number = Constants.phoneNumber
        OTPReceiver().setOtpView(binding.otpVw)
        binding.otpVw.setOtpCompletionListener { otp->
                binding.progressBar.visibility = View.VISIBLE
                checkInUser()
        }
        return binding.root
    }
    private var timer =object: CountDownTimer(60000,1000){
        override fun onTick(p0: Long) {
            val min:Int = (p0/1000).toInt()
            binding.resend.text = getString(R.string.please_wait)+"$min"
            binding.resend.isClickable  = false
        }
        override fun onFinish() {
            binding.resend.text = getString(R.string.resend_otp)
            binding.resend.isClickable  = true
        }
    }
    private fun checkInUser() {
        binding.progressBar.visibility = View.GONE
        timer.cancel()
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }
}