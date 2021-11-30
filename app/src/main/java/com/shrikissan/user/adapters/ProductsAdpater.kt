package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrikissan.user.R
import com.shrikissan.user.models.Product

class ProductsAdapter(private val context: Context, private val list:ArrayList<Product>,val onClick:(pos:Int)->Unit):RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.product_layout,parent,false)
        val holder = ProductsViewHolder(itemView)
        itemView.setOnClickListener {
            onClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, pos: Int) {
         holder.rating.rating = list[pos].rating
         holder.name.text = list[pos].name
        // holder.price.text = context.getString(R.string.rupee) + list[pos].product_cost.toString()
         Glide.with(context)
             .load(list[pos].image)
             .into(holder.image)
    }

    override fun getItemCount(): Int {
       return list.size
    }
}

class ProductsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val name:TextView = itemView.findViewById(R.id.name)
    val image: ImageView = itemView.findViewById(R.id.image)
    val price: TextView = itemView.findViewById(R.id.price)
    val rating:RatingBar = itemView.findViewById(R.id.rating)
}
