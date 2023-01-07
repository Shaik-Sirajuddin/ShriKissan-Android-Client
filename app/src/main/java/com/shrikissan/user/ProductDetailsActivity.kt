package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    private val itemsList = ArrayList<SubProduct>()
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.product_detail)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolBar)
        setContentView(binding.root)
        repository = Repository(this)
        val data = intent
        if (data != null) {
            product = Json.decodeFromString(data.getStringExtra("product").toString())
            val dialog = CustomProgressDialog(this)
            dialog.show()
            repository.getProductDetails(product.id) {
                dialog.dismiss()
                if (it != null) {
                    product = it
                    setValues()
                    binding.tohide.visibility = View.GONE
                } else {
                    binding.tohide.visibility = View.VISIBLE
                }
            }
            itemsList.clear()
            itemsList.addAll(product.itemsList)
        }
        adapter = ReviewsAdapter(this, list)
        quantityAdapter = QuantityListAdapter(this, itemsList) {
            selectedPos = it
            binding.price.text =
                getString(R.string.rupee) + product.itemsList[selectedPos].product_cost
        }
        binding.quantityList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.quantityList.adapter = quantityAdapter
        binding.recyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.recyclerView.adapter = adapter
        binding.buy.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            val item = CartItem("", product, product.itemsList[selectedPos], 1)
            val aList = ArrayList<CartItem>()
            aList.add(item)
            val cartItem = Json.encodeToString(aList)
            intent.putExtra(Constants.orderKey, cartItem)
            startActivity(intent)
        }
        binding.addToCart.setOnClickListener {
            val dial = CustomProgressDialog(this)
            dial.show()
            repository.addCartItem(CartItem("", product, product.itemsList[selectedPos], 1)) {
                dial.dismiss()
                if (it) {
                    showToast("Added")
                } else {
                    showToast("Error Occurred")
                }
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
        product.detailImages.forEach {
            imageList.add(SlideModel(it, ScaleTypes.FIT))
        }
        binding.ratingBar.rating = product.rating
        binding.imageSlider.setImageList(imageList)
        list.clear()
        itemsList.clear()
        list.addAll(product.reviewList)
        itemsList.addAll(product.itemsList)
        adapter.notifyDataSetChanged()
        quantityAdapter.notifyDataSetChanged()
        if (product.itemsList.isNotEmpty()) {
            binding.price.text = getString(R.string.rupee) + product.itemsList[0].product_cost
        }
    }
}