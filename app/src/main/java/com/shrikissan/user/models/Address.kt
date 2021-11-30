package com.shrikissan.user.models

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    var id:String = "",
    var name:String,
    var lane:String,
    var landmark:String,
    var city:String,
    var state:String,
    var phoneNumber:String,
    var pinCode:Int
)