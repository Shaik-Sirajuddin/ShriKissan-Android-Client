package com.shrikissan.user.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import com.shrikissan.user.ChangeLanguage
import com.shrikissan.user.SignInActivity
import com.shrikissan.user.UpdateProfileActivity
import com.shrikissan.user.databinding.FragmentCartScreenBinding
import com.shrikissan.user.databinding.FragmentMoreScreenBinding
import com.shrikissan.user.models.Constants


class MoreScreen : Fragment() {
    private lateinit var binding: FragmentMoreScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoreScreenBinding.inflate(inflater)
        binding.changeAddress.setOnClickListener{

        }
        binding.logOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Do you want to Logout?")
                .setPositiveButton("Yes") { dial, _ ->
                    logOut()
                    dial.dismiss()
                    val intent = Intent(requireContext(),SignInActivity::class.java)
                    startActivity(intent)
                    requireActivity().finishAffinity()
                }
                .setNegativeButton("No") { dial, _ -> dial.dismiss() }
                .show()
        }
        binding.editProfile.setOnClickListener {
            val intent = Intent(requireContext(),UpdateProfileActivity::class.java)
            startActivity(intent)
        }
        binding.changeLanguage.setOnClickListener {
           val intent = Intent(requireContext(),ChangeLanguage::class.java)
            startActivity(intent)
        }
        binding.inviteAndEarn.setOnClickListener {

        }
        binding.contactUs.setOnClickListener {

        }
        binding.privacyPolicy.setOnClickListener {

        }

        return binding.root
    }

    private fun logOut() {
        val data = requireContext().getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
        data.edit {
            putString(Constants.userNumber, "")
            putBoolean(Constants.isLoggedIn, false)
            commit()
        }
    }
}