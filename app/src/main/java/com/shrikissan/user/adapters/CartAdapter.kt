package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrikissan.user.R
import com.shrikissan.user.models.CartItem

class CartAdapter(
    val context: Context,
    private val list: ArrayList<CartItem>,
    private val listener: CartItemListener,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        val holder = CartViewHolder(item)
        item.setOnClickListener {
            listener.itemClicked(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: CartViewHolder, pos: Int) {
        val product = list[pos].product
        Glide.with(context)
            .load(product.image)
            .into(holder.image)
        holder.name.text = product.name
        //holder.price.text = product.product_cost.toString()
        holder.quantity.text = list[pos].quantity.toString()
        holder.increase.setOnClickListener {
            listener.increase(holder.adapterPosition)
        }
        holder.decrease.setOnClickListener {
            listener.decrease(holder.adapterPosition)
        }
        holder.remove.setOnClickListener {
            listener.remove(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.imageView)
    val name: TextView = itemView.findViewById(R.id.productimage)
    val price: TextView = itemView.findViewById(R.id.price)
    val increase: ImageButton = itemView.findViewById(R.id.increasebutton)
    val decrease: ImageButton = itemView.findViewById(R.id.decreasebutton)
    val remove: Button = itemView.findViewById(R.id.remove)
    val quantity: TextView = itemView.findViewById(R.id.quantity)
}

interface CartItemListener {
    fun decrease(pos: Int)
    fun increase(pos: Int)
    fun remove(pos: Int)
    fun itemClicked(pos: Int)
}
