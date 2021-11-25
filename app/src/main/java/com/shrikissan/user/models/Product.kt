package com.shrikissan.user.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    var name:String,
    var image:String,
    var quantity:String,
    var product_cost:Int,
    var mrp_cost:Int,
    var category_id:String,
    var description:String,
    var rating:Float = 0f,
    var reviewList:ArrayList<Review> = ArrayList(),
    var detailImages:ArrayList<String> =ArrayList()
)