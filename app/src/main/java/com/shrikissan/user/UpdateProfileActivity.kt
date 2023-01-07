package com.shrikissan.user

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.shrikissan.user.databinding.ActivityUpdateProfileBinding

import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.bumptech.glide.Glide
import com.shrikissan.user.models.showToast
import com.shrikissan.user.network.Repository
import java.io.ByteArrayOutputStream
import java.io.InputStream


class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var repo: Repository
    private var encodedImage = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repo = Repository(this)
        binding.confirm.setOnClickListener {
            if (binding.name.text.isNotEmpty()) {
                updateProfile()
            } else {
                showToast("Fill name")
            }
        }
        binding.imageView2.setOnClickListener {
            finish()
        }
        binding.changeImage.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        val dialog = CustomProgressDialog(this)
        dialog.show()
        repo.getProfile {
            dialog.dismiss()
            if(it!=null){
                binding.name.setText(it.name)
                binding.mobileNumber.text = it.number
                Glide.with(this)
                    .load(it.image)
                    .into(binding.profile)
            }
        }
    }

    private fun updateProfile() {
        val dialog = CustomProgressDialog(this)
        dialog.show()
        repo.updateProfile(binding.name.text.toString(),encodedImage){
            dialog.dismiss()
            if(it){
                showToast("Profile updated")
                finish()
            }
            else{
                showToast("Unknown error occurred")
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            updateImage(it)
        }
    }

    private fun updateImage(uri: Uri) {
        val imageStream: InputStream? = contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        encodedImage = encodeImage(selectedImage)
        val bytes: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        binding.profile.setImageBitmap(bitmap)
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}