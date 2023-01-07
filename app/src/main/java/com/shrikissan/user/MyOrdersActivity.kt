package com.shrikissan.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikissan.user.adapters.OrdersAdapter
import com.shrikissan.user.databinding.ActivityMyOrdersBinding
import com.shrikissan.user.models.CartItem
import com.shrikissan.user.network.Repository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MyOrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyOrdersBinding
    private lateinit var adapter: OrdersAdapter
    private val list = ArrayList<CartItem>()
    private lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository(this)
        adapter = OrdersAdapter(this, list, false, { click ->
            val intent = Intent(this, ProductDetailsActivity::class.java)
            val product = Json.encodeToString(list[click].product)
            intent.putExtra("product", product)
            startActivity(intent)
        }, { review ->
             val intent = Intent(this,ReviewActivity::class.java)
            intent.putExtra("id",list[review].product.id)
            intent.putExtra("name",list[review].product.name)
            startActivity(intent)
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        val dialog = CustomProgressDialog(this)
        dialog.show()
        repository.getOrders {
            dialog.dismiss()
            binding.tohide.visibility = View.GONE
            if(it!=null){
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
                if(list.isEmpty()){
                    binding.tohide.text = resources.getString(R.string.no_orders)
                    binding.tohide.visibility = View.GONE
                }
            }
            else{
                binding.tohide.text = resources.getString(R.string.network_not_available)
                binding.tohide.visibility = View.VISIBLE
            }
        }
    }
}