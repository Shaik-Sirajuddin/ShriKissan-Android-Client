package com.shrikissan.user.models

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class SubProduct(
    var id:String ="",
    var status:String = "open",
    var quantity:String = "",
    var product_cost:Int = 0,
    var mrp_cost:Int = 0,
) {
    fun setData(obj:JSONObject){
        this.id = obj["_id"].toString()
        this.product_cost = obj["product_cost"] as Int
        this.mrp_cost = obj["mrp_cost"] as Int
        this.quantity = obj["quantity"].toString()
        this.status = obj["status"].toString()
    }
}