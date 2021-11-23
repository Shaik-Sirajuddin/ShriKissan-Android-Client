package com.shrikissan.user.models

import org.json.JSONObject

data class Product(
    var name:String,
    var image:String,
    var price:Int,
    var description:String,
    var rating:Float = 0f,
    var detailImages:ArrayList<String> =ArrayList()
)