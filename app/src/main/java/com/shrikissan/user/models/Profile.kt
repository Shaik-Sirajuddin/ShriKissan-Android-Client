package com.shrikissan.user.models

import android.util.Log
import org.json.JSONObject

data class Profile(
    var name:String = "",
    var number:String = "",
    var image:String = ""
){
    fun setData(obj:JSONObject){
        if(obj.has("name")){
            this.name = obj.getString("name")
        }
        if(obj.has("mobile_num")){
            this.number = obj.getString("mobile_num")
        }
        if(obj.has("profile_picture")){
            val img = obj.getJSONObject("profile_picture")
            this.image = img.getString("image_url")
        }
        Log.e("img",obj.toString())
    }
}