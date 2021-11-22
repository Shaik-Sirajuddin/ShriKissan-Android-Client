package com.shrikissan.user.fragments

import android.Manifest
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
import androidx.core.content.ContextCompat
import com.shrikissan.user.R
import com.shrikissan.user.models.Constants


class MobileNumberScreen : Fragment() {
    private lateinit var binding: FragmentMobileNumberFragmentBinding
    private  var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMobileNumberFragmentBinding.inflate(inflater)
        binding.phoneNumber.requestFocus()
        binding.continueButton.setOnClickListener {
            val number = binding.phoneNumber.editableText.toString().trim()
            if(number.length<10){
                toast?.cancel()
                toast =  Toast.makeText(requireContext(),"Enter A Valid Phone Number",Toast.LENGTH_SHORT)
                toast?.show()
            }
            else{
                Constants.phoneNumber = number
                Navigation.findNavController(binding.root).navigate(R.id.navigate_to_otpScreen)
            }
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