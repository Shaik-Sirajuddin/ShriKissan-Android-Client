package com.shrikissan.user.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.shrikissan.user.adapters.ProductsAdapter
import com.shrikissan.user.databinding.FragmentProductsScreenBinding
import com.shrikissan.user.models.Product


class ProductsScreen : Fragment() {
    private lateinit var binding: FragmentProductsScreenBinding
    private lateinit var adapter: ProductsAdapter
    private val list = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        adapter = ProductsAdapter(requireContext(),list){

        }
        binding = FragmentProductsScreenBinding.inflate(inflater)
        binding.productRecycle.adapter = adapter
        binding.productRecycle.layoutManager = GridLayoutManager(requireContext(),2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isProduct = arguments?.getBoolean("isProduct",false)?:false
        if(isProduct){
            val productName = arguments?.getString("productName").toString()
            searchAndLoadProducts(productName)
        }
        else{
            val category = arguments?.getString("category").toString()
            loadCategoryProducts(category)
        }
        Log.d("is",isProduct.toString()+"k")
    }

    fun loadCategoryProducts(category:String){

    }
    fun searchAndLoadProducts(name:String){

    }
}