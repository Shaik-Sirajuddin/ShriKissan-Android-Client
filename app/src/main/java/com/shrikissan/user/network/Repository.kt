package com.shrikissan.user.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.Telephony
import android.util.Base64
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
                    Log.e("fas", "adfa")
                    onComplete(list)

                } catch (e: JSONException) {
                    Log.e("dfas", "dfasf")
                    e.printStackTrace()
                }
            } else {
                onComplete(null)
            }
        }

    }

    fun getProductsOfACategory(catId: String, onComplete: (ArrayList<Product>?) -> Unit) {
        val url = Shop.baseUrl + Shop.Product.listByCategory
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

    fun getProductsByName(query: String, onComplete: (ArrayList<Product>?) -> Unit) {
        val url = Shop.baseUrl + Shop.Product.getAll
        sendRequest(url, null, Request.Method.GET) { response ->
            if (response != null) {
                try {
                    val data = response.getJSONArray("data")
                    val list = ArrayList<Product>()
                    for (i in 0 until data.length()) {
                        val product = Product()
                        product.setData(data.getJSONObject(i))
                        list.add(product)
                    }
                    val aList = list.filter {
                        it.name.contains(query, true)
                    }
                    list.clear()
                    list.addAll(aList)
                    onComplete(list)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
            else{
                onComplete(null)
            }
        }
    }

    fun getProductDetails(id: String, onComplete: (product: Product?) -> Unit) {
        val url = Shop.baseUrl + Shop.Product.get
        val obj = JSONObject()
        obj.put("product_id", id)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                val data = it.getJSONObject("data")
                val product = Product()
                product.setData(data)
                onComplete(product)
            } else {
                onComplete(null)
            }
        }
    }

    fun getCartItems(onComplete: (ArrayList<CartItem>?) -> Unit) {
        val url = Shop.baseUrl + Shop.User.Cart.get
        sendRequest(url, null, Request.Method.GET) { response ->
            try {
                if (response != null) {
                    val items = response.getJSONObject("data")
                        .getJSONArray("items")
                    val list = ArrayList<CartItem>()
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val cartItem = CartItem()
                        cartItem.setData(item)
                        list.add(cartItem)
                    }
                    onComplete(list)
                } else {
                    onComplete(null)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    fun addAddress(address: Address, onComplete: (done: Boolean) -> Unit) {
        val url = Shop.baseUrl + Shop.Order.addAddress
        val obj = JSONObject()
        obj.put("door_number", address.lane)
        obj.put("city", address.city)
        obj.put("state", address.state)
        obj.put("pincode", address.pinCode)
        obj.put("landmark", address.landmark)
        obj.put("name", address.name)
        obj.put("mobile_num", address.phoneNumber)
        sendRequest(url, obj, Request.Method.PUT) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun removeAddress(address: Address, onComplete: (done: Boolean) -> Unit) {
        val url = Shop.baseUrl + Shop.Order.removeAddress
        val obj = JSONObject()
        obj.put("order_address_id", address.id)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun updateAddress(address: Address, onComplete: (done: Boolean) -> Unit) {
        val url = Shop.baseUrl + Shop.Order.editAddress
        val obj = JSONObject()
        Log.e("id", address.id + "fdsa")
        obj.put("order_address_id", address.id)
        obj.put("door_number", address.lane)
        obj.put("city", address.city)
        obj.put("state", address.state)
        obj.put("pincode", address.pinCode)
        obj.put("landmark", address.landmark)
        obj.put("name", address.name)
        obj.put("mobile_num", address.phoneNumber)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun getAllAddress(onComplete: (ArrayList<Address>?) -> Unit) {
        val url = Shop.baseUrl + Shop.Order.listAddress
        sendRequest(url, null, Request.Method.GET) {
            if (it != null) {
                val data = it.getJSONArray("data")
                val list = ArrayList<Address>()
                for (i in 0 until data.length()) {
                    val obj = data.getJSONObject(i)
                    val address = Address()
                    address.setData(obj)
                    list.add(address)
                }
                onComplete(list)
            } else {
                onComplete(null)
            }
        }
    }

    fun getDeliveryPrice(onComplete: (price: Int) -> Unit) {
        onComplete(40)
    }

    fun removeCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {
        val url = Shop.baseUrl + Shop.User.Cart.delete
        val obj = JSONObject()
        obj.put("order_item_id", cartItem.id)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun updateProfile(
        name: String,
        image: String,
        number: String? = null,
        onComplete: (done: Boolean) -> Unit,
    ) {
        val url = Shop.baseUrl + Shop.User.updateProfile
        val obj = JSONObject()
        val list = JSONArray()
        if (image.trim().isNotEmpty()) {
            list.put(image)
            obj.put("image_urls", list)
        }
        if (number != null) {
            obj.put("mobile_num", number)
        }
        if (name.trim().isNotEmpty()) {
            obj.put("name", name)
        }
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun addProfile(profile: Profile, onComplete: (done: Boolean) -> Unit) {
        updateProfile(profile.name, profile.image, profile.number) {
            onComplete(it)
        }
    }

    fun getProfile(onComplete: (profile: Profile?) -> Unit) {
        val url = Shop.baseUrl + Shop.User.get
        sendRequest(url, null, Request.Method.GET) {
            if (it != null) {
                val data = it.getJSONObject("data")
                val profile = Profile()
                profile.setData(data)
                onComplete(profile)
            } else {
                onComplete(null)
            }
        }
    }

    fun addCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {
        checkItemContains(cartItem) { have ->
            if (have != null) {
                increaseCartItem(have) {
                    onComplete(it)
                }
                Log.e("error", "into")
            } else {
                val url = Shop.baseUrl + Shop.User.Cart.add
                val obj = JSONObject()
                obj.put("product_id", cartItem.product.id)
                obj.put("item_id", cartItem.subProduct.id)
                sendRequest(url, obj, Request.Method.PUT) {
                    if (it != null) {
                        onComplete(true)
                    } else {
                        onComplete(false)
                    }
                }
            }
        }

    }

    private fun checkItemContains(cartItem: CartItem, onComplete: (item: CartItem?) -> Unit) {
        getCartItems {
            if (it != null) {
                val list = it.filter { it1 ->
                    it1.product.id == cartItem.product.id &&
                            it1.subProduct.id == cartItem.subProduct.id
                }
                if (list.isNotEmpty()) {
                    onComplete(list[0])
                } else {
                    onComplete(null)
                }
            } else {
                onComplete(null)
            }
        }
    }

    fun increaseCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {
        val url = Shop.baseUrl + Shop.User.Cart.increase
        val obj = JSONObject()
        obj.put("order_item_id", cartItem.id)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun decreaseCartItem(cartItem: CartItem, onComplete: (done: Boolean) -> Unit) {
        val url = Shop.baseUrl + Shop.User.Cart.decrease
        val obj = JSONObject()
        obj.put("order_item_id", cartItem.id)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    fun getPaymentToken(price: Int, onComplete: (token: String?, orderId: String?) -> Unit) {

    }

    fun getWalletAmount(onComplete: (price: Int) -> Unit) {
        onComplete(100)
    }

    fun getOrders(onComplete: (list: ArrayList<CartItem>?) -> Unit) {
       val url = Shop.baseUrl+Shop.Order.listOrders
        sendRequest(url, null, Request.Method.GET) { response ->
            try {
                if (response != null) {
                    val data = response.getJSONArray("data")
                    val list = ArrayList<CartItem>()
                    for(j in 0 until data.length()){
                        val dataItem = data.getJSONObject(j)
                        val subList = dataItem.getJSONArray("items")
                        for (i in 0 until subList.length()) {
                            val item = subList.getJSONObject(i)
                            val cartItem = CartItem()
                            cartItem.setData(item)
                            list.add(cartItem)
                        }
                    }
                    onComplete(list)
                } else {
                    onComplete(null)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("error",e.message.toString())
            }
        }
    }

    fun uploadAndGetUri(data: String, onComplete: (url: String?) -> Unit) {
        val url = Shop.baseUrl + Shop.User.putAndGet
        val obj = JSONObject()
        val arr = JSONArray()
        arr.put(data)
        obj.put("image_urls", arr)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                Log.e("upload",it.toString())
                val da = it.getJSONArray("data")
                if(da.length()>0){
                    val string = da.getString(0)
                    onComplete(string)
                }
                else{
                    onComplete(null)
                }
            } else {
                onComplete(null)
            }
        }
    }
    fun addReview(pId:String,review: Review,onComplete:(done:Boolean)->Unit){
        val url = Shop.baseUrl + Shop.Order.addReview
        val obj = JSONObject()
        val arr = JSONArray()
        arr.put(review.images)
        obj.put("description","")
        obj.put("product_id",pId)
        obj.put("rating",review.rating)
        obj.put("image_urls", arr)
        sendRequest(url, obj, Request.Method.POST) {
            if (it != null) {
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }
    fun getReview(pId:String,onComplete: (review: Review?) -> Unit){
        val url = Shop.baseUrl + Shop.Order.getReview
        val obj = JSONObject()
        obj.put("product_id",pId)
        sendRequest(url,obj,Request.Method.GET){
            if(it!=null){
                val data = it.getJSONArray("data")
                if(data.length()>0){
                    val a = data.getJSONObject(0)
                    val rev = Review()
                    rev.setData(a)
                    onComplete(rev)
                }
                else{
                    onComplete(null)
                }
            }
            else{
                onComplete(null)
            }
        }
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