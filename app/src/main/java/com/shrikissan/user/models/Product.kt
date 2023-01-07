package com.shrikissan.user.models

import kotlinx.serialization.Serializable
import org.json.JSONArray
import org.json.JSONObject

@Serializable
data class Product(
    var id: String = "",
    var name: String = "",
    var image: String = "",
    var category_id: String = "",
    var description: String = "",
    var rating: Float = 0f,
    var reviewList: ArrayList<Review> = ArrayList(),
    var detailImages: ArrayList<String> = ArrayList(),
    var itemsList: ArrayList<SubProduct> = ArrayList(),
) {
    fun setData(obj: JSONObject) {
        this.id = obj["_id"].toString()
        this.name = obj["name"].toString()
        this.description = obj["description"].toString()
        if(obj.has("rating")){
            this.rating = obj.getDouble("rating").toFloat()
        }
        val imgList = obj.getJSONArray("images")
        for( i in 0 until imgList.length()){
            detailImages.add(imgList.getJSONObject(i).getString("image_url"))
        }
        if(imgList.length()>0){
            image = imgList.getJSONObject(0).getString("image_url")
        }
        if (obj.has("items")) {
            itemsList.clear()
            val items = obj.getJSONArray("items")
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val product = SubProduct()
                product.setData(item)
                itemsList.add(product)
            }
        }
        if(obj.has("reviews")){
            reviewList.clear()
            val reviews = obj.getJSONArray("reviews")
            for( i in 0 until reviews.length()){
                val rev = Review()
                rev.setData(reviews.getJSONObject(i))
                reviewList.add(rev)
            }
        }
    }
}