package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shrikissan.user.R
import com.shrikissan.user.models.Address

class AddressAdapter(
    val context: Context,
    val list: ArrayList<Address>,
    val disable: Boolean = false,
    private val listener: AddressCallBacks,
) : RecyclerView.Adapter<AddressViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.address_edit_item, parent, false)
        val holder = AddressViewHolder(item)
        holder.edit.setOnClickListener {
            listener.editAddress(holder.adapterPosition)
        }
        holder.remove.setOnClickListener {
            listener.removeAddress(holder.adapterPosition)
        }
        item.setOnClickListener {
            listener.itemClicked(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: AddressViewHolder, pos: Int) {
        holder.name.text = list[pos].name
        holder.lane.text = list[pos].lane
        holder.landMark.text = list[pos].landmark
        holder.all.text = list[pos].city + "," + list[pos].state + "," + list[pos].pinCode
        holder.number.text = list[pos].phoneNumber.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val lane: TextView = itemView.findViewById(R.id.lane)
    val landMark: TextView = itemView.findViewById(R.id.landMark)
    val all: TextView = itemView.findViewById(R.id.start)
    val number: TextView = itemView.findViewById(R.id.phoneNumber)
    val edit: Button = itemView.findViewById(R.id.edit)
    val remove: Button = itemView.findViewById(R.id.remove)
}

interface AddressCallBacks {
    fun editAddress(pos: Int)
    fun removeAddress(pos: Int)
    fun itemClicked(pos: Int)
}