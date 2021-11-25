package com.shrikissan.user.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.shrikissan.user.R
import com.shrikissan.user.adapters.ProductsAdapter
import com.shrikissan.user.databinding.FragmentProductsScreenBinding
import com.shrikissan.user.models.Product
import com.shrikissan.user.network.Repository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ProductsScreen : Fragment() {
    private lateinit var binding: FragmentProductsScreenBinding
    private lateinit var adapter: ProductsAdapter
    private val list = ArrayList<Product>()
    private lateinit var repository: Repository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        adapter = ProductsAdapter(requireContext(),list){
            val product = Json.encodeToString(list[it])
            val bundle = bundleOf("product" to product)
            Navigation.findNavController(binding.root).navigate(R.id.navigateToProductDetailScreen,bundle)
        }
        repository = Repository(requireContext())
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

    private fun loadCategoryProducts(category:String){
        repository.getProductsOfACategory{
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
    private fun searchAndLoadProducts(name:String){
         repository.getProductsByName(name){
             list.clear()
             list.addAll(it)
             adapter.notifyDataSetChanged()
         }
    }
}