package com.shrikissan.user.models

import kotlinx.serialization.Serializable
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Serializable
data class Review(
    var id:String = "",
    var name:String = "",
    var reviewerImage:String = "",
    var date:Long = 0,
    var rating:Float = 0f,
    var reviewText:String = "",
    var images:ArrayList<String> = ArrayList()
){
    fun setData(obj:JSONObject){
        this.id = obj["_id"].toString()
        //this.date = obj["createdAt"].toString().toLong()
        this.rating  = obj["rating"].toString().toFloat()
        this.reviewText = obj["description"].toString()
        if(obj.has("images")){
            images.clear()
            val array = obj.getJSONArray("images")
            for(i in 0 until array.length()){
                val url = array.getJSONObject(i).getString("image_url")
                images.add(url)
            }
        }
    }
}
