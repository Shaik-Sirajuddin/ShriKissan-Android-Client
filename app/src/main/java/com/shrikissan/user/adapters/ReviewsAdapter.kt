package com.shrikissan.user.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.shrikissan.user.R
import com.shrikissan.user.models.Review
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReviewsAdapter(private val context:Context, private val list:ArrayList<Review>):RecyclerView.Adapter<ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
      val view = LayoutInflater.from(context).inflate(R.layout.review_item,parent,false)
      val holder = ReviewViewHolder(view)
      return holder
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, pos: Int) {
        holder.name.text = list[pos].name
        holder.reviewText.text = list[pos].reviewText
        holder.date.text = SimpleDateFormat("dd::mm:yy").format(Date(list[pos].date))
        holder.rating.rating = list[pos].rating
        val imageList = ArrayList<SlideModel>()
        list[pos].images.forEach {
            imageList.add(SlideModel(it,ScaleTypes.FIT))
        }
        holder.imageSlider.setImageList(imageList)
        holder.imageSlider.stopSliding()
        Glide.with(context)
            .load(list[pos].reviewerImage)
            .placeholder(R.drawable.profile)
            .into(holder.reviewerImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ReviewViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
   val name: TextView = itemView.findViewById(R.id.name)
   val reviewerImage:CircleImageView = itemView.findViewById(R.id.circleImageView)
   val date:TextView = itemView.findViewById(R.id.date)
   val imageSlider:ImageSlider = itemView.findViewById(R.id.reviewImages)
   val rating:RatingBar = itemView.findViewById(R.id.rating)
   val reviewText:TextView = itemView.findViewById(R.id.reviewText)
}
