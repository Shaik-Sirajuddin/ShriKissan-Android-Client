package com.shrikissan.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrikissan.user.R
import com.shrikissan.user.databinding.FragmentOrderPageBinding
import com.shrikissan.user.models.Address
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class OrderPage : Fragment() {
    private lateinit var binding:FragmentOrderPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrderPageBinding.inflate(layoutInflater)
        binding.continueButton.setOnClickListener {
            startPayment()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addressString = arguments?.getString("address")?:""
        val address = Json.decodeFromString<Address>(addressString)

    }
    private fun startPayment() {

    }
}