package com.shrikissan.user.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.shrikissan.user.ContactUsActivity
import com.shrikissan.user.R
import com.shrikissan.user.adapters.CategoryAdapter
import com.shrikissan.user.databinding.FragmentCategoryScreenBinding
import com.shrikissan.user.models.CategoryItem
import com.shrikissan.user.models.isConnected
import com.shrikissan.user.network.Repository

class CategoryScreen : Fragment() {
    private lateinit var binding:FragmentCategoryScreenBinding
    private lateinit var adapter: CategoryAdapter
    private val list = ArrayList<CategoryItem>()
    private lateinit var view1:View
    private lateinit var repository: Repository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoryScreenBinding.inflate(inflater)
        adapter = CategoryAdapter(requireContext(),list){
            val bundle = bundleOf("category" to list[it].id)
            Navigation.findNavController(binding.root).navigate(R.id.navigateToProductScreen,bundle)
        }
        binding.soilTest.setOnClickListener {
            val intent = Intent(requireContext(),ContactUsActivity::class.java)
            startActivity(intent)
        }
        binding.contactUs.setOnClickListener {
            val intent = Intent(requireContext(),ContactUsActivity::class.java)
            startActivity(intent)
        }
        repository = Repository(requireContext())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.adapter = adapter
        view1 = binding.root
        checkNetworkAndLoad()
        return binding.root
    }
    private fun checkNetworkAndLoad(){
        if(requireActivity().isConnected()){
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.noNetwork.visibility = View.VISIBLE
        }
        loadCategories()
    }
    private fun loadCategories(){
        repository.getCategories {
            binding.progressBar.visibility = View.GONE
            binding.noNetwork.visibility = View.GONE
            if(it!=null){
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
                if(list.isEmpty()){
                    binding.noNetwork.text = resources.getString(R.string.no_category)
                    binding.noNetwork.visibility = View.VISIBLE
                }
            }
            else{
                binding.noNetwork.text = resources.getString(R.string.network_not_available)
                binding.noNetwork.visibility = View.VISIBLE
            }
        }
    }

}