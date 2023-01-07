package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrikissan.user.databinding.ActivityOrderCompletedBinding

class OrderCompletedActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrderCompletedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderId = intent.getStringExtra("orderId")?:""
        val refId = intent.getStringExtra("refId")?:""
        binding.orderId.text = getString(R.string.order_id)+orderId
        binding.refId.text = getString(R.string.ref_id)+refId
        binding.button.setOnClickListener {
            val intent = Intent(this,MyOrdersActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}