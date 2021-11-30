package com.shrikissan.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrikissan.user.databinding.ActivityOrderBinding
import com.shrikissan.user.models.CartItem
import com.shrikissan.user.models.Constants
import com.shrikissan.user.models.Product
import com.shrikissan.user.models.SubProduct
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class OrderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.getStringExtra(Constants.orderKey)?:""
        Constants.orderList =  Json.decodeFromString(item)
    }
}