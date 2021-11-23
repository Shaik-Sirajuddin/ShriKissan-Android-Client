package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shrikissan.user.R
import com.shrikissan.user.models.CategoryItem

class CategoryAdapter(private val context:Context,private val list:ArrayList<CategoryItem>,val onClick:(pos:Int)->Unit): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false)
        val holder = CategoryViewHolder(itemView)
        itemView.setOnClickListener {
            onClick(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, pos: Int) {
       holder.name.text = list[pos].name
        Glide.with(context)
            .load(list[pos])
            .into(holder.image)
    }

    override fun getItemCount(): Int {
      return list.size
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name:TextView = itemView.findViewById(R.id.name)
    val image: ImageView = itemView.findViewById(R.id.image)
}
