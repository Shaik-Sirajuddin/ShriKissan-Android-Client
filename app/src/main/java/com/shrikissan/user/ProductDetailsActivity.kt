package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shrikissan.user.adapters.QuantityListAdapter
import com.shrikissan.user.adapters.ReviewsAdapter
import com.shrikissan.user.databinding.ActivityProductDetailsBinding
import com.shrikissan.user.models.*
import com.shrikissan.user.network.Repository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var product: Product
    private lateinit var adapter: ReviewsAdapter
    private val list = ArrayList<Review>()
    private lateinit var quantityAdapter: QuantityListAdapter
    private var selectedPos = 0
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository(this)
        val data = intent
        if (data != null) {
            product = Json.decodeFromString(data.getStringExtra("product").toString())
            setValues()
        }
        binding.buy.setOnClickListener {
            val intent = Intent(this,OrderActivity::class.java)
            val item =CartItem(product,product.itemsList[selectedPos],1)
            val aList = ArrayList<CartItem>()
            aList.add(item)
            val cartItem = Json.encodeToString(aList)
            intent.putExtra(Constants.orderKey,cartItem)
            startActivity(intent)
        }
        binding.addToCart.setOnClickListener {
            val dial = CustomProgressDialog(this)
            dial.show()
            repository.addCartItem(CartItem(product, product.itemsList[selectedPos], 1)) {
                dial.dismiss()
                if(it)
                showToast("Added")
            }
        }
        binding.imageView2.setOnClickListener {
            finish()
        }

    }

    private fun setValues() {
        binding.name.text = product.name
        binding.desciption.text = product.description
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://i.picsum.photos/id/402/200/300.jpg?hmac=JmZsqnQgJgxs4tbKwb8Tdu3r-B0tEGN7nrKEb1jBB0Y",
            ScaleTypes.CENTER_INSIDE))
        product.detailImages.forEach {
            imageList.add(SlideModel(it, ScaleTypes.CENTER_INSIDE))
        }
        binding.imageSlider.setImageList(imageList)
        list.addAll(product.reviewList)
        adapter = ReviewsAdapter(this, list)
        quantityAdapter = QuantityListAdapter(this, product.itemsList) {
            selectedPos = it
            binding.price.text =
                getString(R.string.rupee) + product.itemsList[selectedPos].product_cost
        }
        binding.quantityList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.quantityList.adapter = quantityAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        if (product.itemsList.isNotEmpty()) {
            binding.price.text = getString(R.string.rupee) + product.itemsList[0].product_cost
        }
    }
}