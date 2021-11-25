package com.shrikissan.user.models

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    var name:String,
    var reviewerImage:String,
    var date:Long,
    var rating:Float,
    var reviewText:String,
    var images:ArrayList<String> = ArrayList()
)
