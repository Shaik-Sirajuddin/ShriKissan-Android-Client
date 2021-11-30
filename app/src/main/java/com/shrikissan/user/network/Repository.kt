package com.shrikissan.user.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.Telephony
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import com.android.volley.VolleyError

import com.android.volley.Response

import org.json.JSONException

import org.json.JSONObject

import org.json.JSONArray

import com.android.volley.toolbox.Volley

import com.android.volley.RequestQueue
import com.shrikissan.user.models.*


class Repository(val context: Context) {
    private val tag = "repo"
    private val queue = MySingleton.getInstance(context.applicationContext).requestQueue
    fun getCategories(onComplete: (ArrayList<CategoryItem>?) -> Unit) {
        val url = Shop.baseUrl + Shop.Category.list
        sendRequest(url, null, Request.Method.GET) { response ->
            if (response != null) {
                try {
                    val array = response.getJSONArray("data")
                    val list = ArrayList<CategoryItem>()
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val category = CategoryItem()
                        category.setData(obj)
                        list.add(category)
                    }
                    onComplete(list)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }

    }

    fun getProductsOfACategory(catId: String, onComplete: (ArrayList<Product>?) -> Unit) {
        val url = Shop.baseUrl + Shop.Product.list
        val obj = JSONObject()
        obj.put("category_id", catId)
        sendRequest(url, obj, Request.Method.POST) { response ->
            if (response != null) {
                try {
                    val data = response.getJSONArray("data")
                    val list = ArrayList<Product>()
                    for (i in 0 until data.length()) {
                        val product = Product()
                        product.setData(data.getJSONObject(i))
                        list.add(product)
                    }
                    onComplete(list)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

        }
    }

    fun getProductsByName(query: String, onComplete: (ArrayList<Product>) -> Unit) {

    }

    fun getCartItems(onComplete: (ArrayList<CartItem>) -> Unit) {
        val url = Shop.baseUrl + Shop.Category.list
        sendRequest(url, null, Request.Method.GET) { response ->
            try {

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

    }
    fun addAddress(address: Address){

    }
    fun removeAddress(address: Address){

    }
    fun updateAddress(address: Address){

    }
    fun getAllAddress(onComplete: (ArrayList<Address>?) -> Unit){

    }
    fun removeCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {

    }

    fun addCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {

    }

    fun increaseCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {

    }

    fun decreaseCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {

    }

    private fun sendRequest(
        url: String,
        body: JSONObject?,
        method: Int,
        onComplete: (response: JSONObject?) -> Unit,
    ) {

        val jsonObjectRequest = object : JsonObjectRequest(method, url, body,
            { response ->
                onComplete(response)
            }, { error ->
                error.printStackTrace()
                onComplete(null)
                Log.e(tag, error.message.toString())
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["x-jwt-token"] = Constants.offlineToken
                return map
            }
        }
        queue.add(jsonObjectRequest)
    }
}