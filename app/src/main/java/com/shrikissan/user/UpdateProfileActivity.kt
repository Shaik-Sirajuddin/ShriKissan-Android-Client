package com.shrikissan.user

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.shrikissan.user.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.confirm.setOnClickListener {

        }
        binding.imageView2.setOnClickListener {
            finish()
        }
        binding.changeImage.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        binding.confirm.setOnClickListener {
            updateProfile()
        }
    }

    private fun updateProfile() {
        finish()
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
       if(it!=null){
           updateImage(it)
       }
    }
    private fun updateImage(uri: Uri){

    }
}