package com.shrikissan.user.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.shrikissan.user.databinding.FragmentMobileNumberFragmentBinding
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import com.shrikissan.user.MainActivity
import com.shrikissan.user.R
import com.shrikissan.user.SignInActivity
import com.shrikissan.user.models.Constants
import com.shrikissan.user.models.showToast


class MobileNumberScreen : Fragment()
{
    private lateinit var binding: FragmentMobileNumberFragmentBinding
    private var toast: Toast? = null
    private lateinit var data: SharedPreferences
    private lateinit var number:String
    private lateinit var activ:SignInActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMobileNumberFragmentBinding.inflate(inflater)
        try {
            binding.phoneNumber.requestFocus()
            binding.phoneNumber.addTextChangedListener {
                if (it.toString().length < 10) {
                    binding.continueButton.setBackgroundResource(R.color.grey)
                    binding.continueButton.isClickable = false
                } else {
                    binding.continueButton.setBackgroundResource(R.color.greenDark)
                    binding.continueButton.isClickable = true
                }
            }
            binding.continueButton.setOnClickListener {
                number = binding.phoneNumber.editableText.toString().trim()
                if (number.length < 10) {
                    toast?.cancel()
                    toast = Toast.makeText(requireContext(),
                        "Enter A Valid Phone Number",
                        Toast.LENGTH_SHORT)
                    toast?.show()
                } else {
                    Constants.phoneNumber = number
                    val activity = requireActivity() as SignInActivity
                    activity.sendOTP()
                    activity.showOTPDialog()
                    //Navigation.findNavController(binding.root).navigate(R.id.navigate_to_otpScreen)
                }
            }
        } catch (e: Exception) {
            Log.e("error", e.message.toString())
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
    }
    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.RECEIVE_SMS
            ), 100)
        }
    }

}