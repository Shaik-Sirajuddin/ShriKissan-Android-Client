package com.shrikissan.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrikissan.user.databinding.FragmentProductDetailsScreenBinding
import com.shrikissan.user.databinding.FragmentProductsScreenBinding


class ProductDetailsScreen : Fragment() {
    private lateinit var binding:FragmentProductDetailsScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductDetailsScreenBinding.inflate(inflater)
        binding.buy.setOnClickListener {

        }
        binding.addToCart.setOnClickListener {

        }
        return binding.root
    }

}