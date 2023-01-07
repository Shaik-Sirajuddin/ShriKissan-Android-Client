package com.shrikissan.user.models


import org.json.JSONObject

data class CategoryItem(
    var name: String = "",
    var imageUrl: String = "",
    var id: String = "",
) {
    fun setData(obj: JSONObject) {
        this.id = obj["_id"].toString()
        this.name = obj["name"].toString()
        if (obj.has("images")) {
            val arr = obj.getJSONArray("images")
            if (arr.length() > 0) {
                this.imageUrl = arr.getJSONObject(0).getString("image_url")
            }
        }
    }
}