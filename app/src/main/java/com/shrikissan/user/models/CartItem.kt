package com.shrikissan.user.models

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class CartItem(
    var id:String = "",
    var product: Product = Product(),
    var subProduct: SubProduct = SubProduct(),
    var quantity:Int = 0,
){
    fun setData(obj:JSONObject){
        this.id = obj["_id"].toString()
        val pro = obj.getJSONObject("product")
        val prod = Product()
        prod.setData(pro)
        this.product  = prod
        this.quantity = obj["count"].toString().toInt()
        val itemobj = obj.getJSONObject("item")
        val item = SubProduct()
        item.setData(itemobj)
        this.subProduct = item
    }
}
