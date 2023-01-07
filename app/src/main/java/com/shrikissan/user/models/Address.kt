package com.shrikissan.user.models

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class Address(
    var id:String = "",
    var name:String = "",
    var lane:String = "",
    var landmark:String = "",
    var city:String = "",
    var state:String = "",
    var phoneNumber:String = "",
    var pinCode:Int = 0
){
    fun setData(obj:JSONObject){
        this.id = obj["_id"].toString()
        this.name = obj["name"].toString()
        this.landmark = obj["landmark"].toString()
        this.lane = obj["door_number"].toString()
        this.city = obj["city"].toString()
        this.state = obj["state"].toString()
        this.phoneNumber = obj["mobile_num"].toString()
        this.pinCode = obj["pincode"].toString().toInt()
    }
}