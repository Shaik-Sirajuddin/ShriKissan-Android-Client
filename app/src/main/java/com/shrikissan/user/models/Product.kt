package com.shrikissan.user.models

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class Product(
    var id:String = "",
    var name:String = "",
    var image:String= "",
    var category_id:String= "",
    var description:String= "",
    var rating:Float = 0f,
    var reviewList:ArrayList<Review> = ArrayList(),
    var detailImages:ArrayList<String> =ArrayList(),
    var itemsList:ArrayList<SubProduct> = ArrayList()
){
    fun setData(obj:JSONObject){
        this.id = obj["_id"].toString()
        this.name = obj["name"].toString()
        this.description = obj["description"].toString()
        val imgList = obj.getJSONArray("images")
        /*for( i in 0 until imgList.length()){

        }*/
        val items = obj.getJSONArray("items")
        for( i in 0 until items.length()){
            val item = items.getJSONObject(i)
            val product = SubProduct()
            product.setData(item)
            this.itemsList.add(product)
        }
    }
}