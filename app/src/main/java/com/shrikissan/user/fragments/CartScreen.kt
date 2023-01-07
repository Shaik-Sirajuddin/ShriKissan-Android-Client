package com.shrikissan.user.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikissan.user.CustomProgressDialog
import com.shrikissan.user.OrderActivity
import com.shrikissan.user.ProductDetailsActivity
import com.shrikissan.user.R
import com.shrikissan.user.adapters.CartAdapter
import com.shrikissan.user.adapters.CartItemListener
import com.shrikissan.user.databinding.FragmentCartScreenBinding
import com.shrikissan.user.models.CartItem
import com.shrikissan.user.models.Constants
import com.shrikissan.user.models.isConnected
import com.shrikissan.user.models.showToast
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
        adapter = CartAdapter(requireContext(), list, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        repository = Repository(requireContext())
        loadItems()
        if (!requireActivity().isConnected()) {
            binding.progressBar.visibility = View.GONE
        }
        binding.materialButton.setOnClickListener {
            val intent = Intent(requireContext(), OrderActivity::class.java)
            val cartItem = Json.encodeToString(list)
            intent.putExtra(Constants.orderKey, cartItem)
            startActivity(intent)
        }
        return binding.root
    }

    override fun decrease(pos: Int) {
        val dial = CustomProgressDialog(requireContext())
        dial.show()
        repository.decreaseCartItem(list[pos]){
            dial.dismiss()
            if(it){
                val item = list[pos]
                --item.quantity
                if(item.quantity==0){
                    list.removeAt(pos)
                    adapter.notifyItemRemoved(pos)
                }
                else{
                    list[pos] = item
                    adapter.notifyItemChanged(pos)
                }
            }
            else{
                requireActivity().showToast("Unknown error occurred")
            }

        }
    }

    override fun increase(pos: Int) {
        val dialog = CustomProgressDialog(requireContext())
        dialog.show()
        repository.increaseCartItem(list[pos]){
            dialog.dismiss()
            if(it){
                val item = list[pos]
                ++item.quantity
                list[pos] = item
                adapter.notifyItemChanged(pos)
            }
            else{
                requireActivity().showToast("Unknown error occurred")
            }
        }
    }

    override fun remove(pos: Int) {
        val dialog = CustomProgressDialog(requireContext())
        dialog.show()
        repository.removeCartItem(list[pos]){
           dialog.dismiss()
            if(it){
                list.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                if (list.isEmpty()) {
                    binding.tohide.text = resources.getString(R.string.empty_cart)
                    binding.tohide.visibility = View.VISIBLE
                }
                else{
                    binding.tohide.visibility = View.GONE
                }
            }
            else{
                requireActivity().showToast("Unknown error occurred")
            }
        }
    }

    override fun itemClicked(pos: Int) {
        val product = Json.encodeToString(list[pos].product)
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

    private fun loadItems() {
        binding.progressBar.visibility = View.VISIBLE
        repository.getCartItems {
            binding.progressBar.visibility = View.GONE
            binding.tohide.visibility = View.GONE
            if (it != null) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
                if (list.isEmpty()) {
                    binding.tohide.text = resources.getString(R.string.empty_cart)
                    binding.tohide.visibility = View.VISIBLE
                }
            } else {
                binding.tohide.text = resources.getString(R.string.network_not_available)
                binding.tohide.visibility = View.VISIBLE
            }

        }
    }
}