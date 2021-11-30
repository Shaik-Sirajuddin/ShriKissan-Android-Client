package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shrikissan.user.R
import com.shrikissan.user.models.SubProduct

class QuantityListAdapter(
    val context: Context,
    val list: ArrayList<SubProduct>,
    val setSelection:(pos:Int)->Unit
) : RecyclerView.Adapter<QuantityViewHolder>() {
    var selectedPos = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuantityViewHolder {
        val item = LayoutInflater.from(context)
            .inflate(R.layout.quantity_item,parent,false)
        val holder = QuantityViewHolder(item)
        item.setOnClickListener {
            val tPos = selectedPos
            selectedPos = holder.adapterPosition
            notifyItemChanged(tPos)
            notifyItemChanged(selectedPos)
            setSelection(selectedPos)
        }
        return holder
    }

    override fun onBindViewHolder(holder: QuantityViewHolder, pos: Int) {
        if(pos==selectedPos){
         holder.quantity.setTextColor(ContextCompat.getColor(context,R.color.white))
         holder.layout.setBackgroundResource(R.drawable.quan)
        }
        else{
            holder.quantity.setTextColor(ContextCompat.getColor(context,R.color.black))
            holder.layout.setBackgroundResource(R.drawable.quan1)
        }
        holder.quantity.text = list[pos].quantity
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class QuantityViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val layout:LinearLayout = itemView.findViewById(R.id.layout)
    val quantity: TextView = itemView.findViewById(R.id.text)
}
