package com.shrikissan.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrikissan.user.databinding.FragmentCartScreenBinding
import com.shrikissan.user.databinding.FragmentMoreScreenBinding


class MoreScreen : Fragment() {
    private lateinit var binding: FragmentMoreScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoreScreenBinding.inflate(inflater)
        binding.changeAddress.setOnClickListener{

        }
        return binding.root
    }
}