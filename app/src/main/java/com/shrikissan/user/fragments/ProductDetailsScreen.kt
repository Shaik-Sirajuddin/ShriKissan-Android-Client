package com.shrikissan.user.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shrikissan.user.R
import com.shrikissan.user.adapters.ReviewsAdapter
import com.shrikissan.user.databinding.FragmentProductDetailsScreenBinding
import com.shrikissan.user.databinding.FragmentProductsScreenBinding
import com.shrikissan.user.models.Product
import com.shrikissan.user.models.Review
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class ProductDetailsScreen : Fragment() {
    private lateinit var binding:FragmentProductDetailsScreenBinding
    private lateinit var product: Product
    private lateinit var adapter:ReviewsAdapter
    private val list = ArrayList<Review>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductDetailsScreenBinding.inflate(inflater)
        binding.buy.setOnClickListener {

        }
        binding.addToCart.setOnClickListener {

        }
        val data = arguments
        if(data!=null){
            product = Json.decodeFromString(data.get("product").toString())
            setValues()
        }
        return binding.root
    }
    private fun setValues(){
        binding.name.text = product.name
        binding.price.text = getString(R.string.rupee) + product.product_cost
        binding.desciption.text = product.description
        val imageList = ArrayList<SlideModel>()
        product.detailImages.forEach {
            imageList.add(SlideModel(it,ScaleTypes.FIT))
        }
        binding.imageSlider.setImageList(imageList)
        list.addAll(product.reviewList)
        adapter = ReviewsAdapter(requireContext(),list)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}