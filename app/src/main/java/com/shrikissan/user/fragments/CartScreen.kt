package com.shrikissan.user.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikissan.user.ProductDetailsActivity
import com.shrikissan.user.adapters.CartAdapter
import com.shrikissan.user.adapters.CartItemListener
import com.shrikissan.user.databinding.FragmentCartScreenBinding
import com.shrikissan.user.models.CartItem
import com.shrikissan.user.models.isConnected
import com.shrikissan.user.network.Repository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CartScreen : Fragment(), CartItemListener {

    private lateinit var binding: FragmentCartScreenBinding
    private lateinit var adapter: CartAdapter
    private lateinit var repository: Repository
    private val list = ArrayList<CartItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartScreenBinding.inflate(inflater)
        adapter = CartAdapter(requireContext(),list,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        repository = Repository(requireContext())
        loadItems()
        if(!requireActivity().isConnected()){
          binding.progressBar.visibility = View.GONE
        }
        return binding.root
    }

    override fun decrease(pos: Int) {
       adapter.notifyItemChanged(pos)
    }

    override fun increase(pos: Int) {
        adapter.notifyItemChanged(pos)
    }

    override fun remove(pos: Int) {
        adapter.notifyItemRemoved(pos)
    }

    override fun itemClicked(pos: Int) {
        val product = Json.encodeToString(list[pos].product)
        val intent = Intent(requireContext(),ProductDetailsActivity::class.java)
        intent.putExtra("product",product)
        startActivity(intent)
    }
    private fun loadItems(){
        binding.progressBar.visibility = View.VISIBLE
        repository.getCartItems {
            binding.progressBar.visibility = View.GONE
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}