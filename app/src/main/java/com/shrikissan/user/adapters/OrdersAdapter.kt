package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrikissan.user.R
import com.shrikissan.user.models.CartItem

class OrdersAdapter(
    val context: Context,
    val list: ArrayList<CartItem>,
    private val isOrder:Boolean= false,
    val itemClick: (pos: Int) -> Unit,
    val review: (pos: Int) -> Unit,
) : RecyclerView.Adapter<OrdersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.checkout_order_item, parent, false)
        val holder = OrdersViewHolder(item)
        item.setOnClickListener {
            itemClick(holder.adapterPosition)
        }
        holder.review.setOnClickListener {
            review(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, pos: Int) {
        Glide.with(context)
            .load(list[pos].product.image)
            .placeholder(R.drawable.profile)
            .into(holder.image)
        holder.quantity.text = list[pos].quantity.toString()
        holder.price.text = list[pos].subProduct.product_cost.toString()
        holder.name.text = list[pos].product.name
        if(isOrder){
            holder.review.visibility = View.GONE
        }
        else{
            holder.review.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
    val quantity: TextView = itemView.findViewById(R.id.quantity)
    val price: TextView = itemView.findViewById(R.id.price)
    val name: TextView = itemView.findViewById(R.id.title)
    val review: Button = itemView.findViewById(R.id.review)
}
