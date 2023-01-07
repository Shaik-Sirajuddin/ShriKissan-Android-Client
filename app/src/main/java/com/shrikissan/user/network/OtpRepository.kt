package com.shrikissan.user.network

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.shrikissan.user.models.Constants
import org.json.JSONException
import org.json.JSONObject

class OtpRepository(val context: Context) {
    private val queue = MySingleton.getInstance(context.applicationContext).requestQueue
    private val tag = "OtpRepo"
    val data = context.getSharedPreferences(Constants.preference, Context.MODE_PRIVATE)
    fun sendOTP(number:String,onComplete:(transId:String?)->Unit){
        val url = Shop.baseUrl+Shop.Auth.login
        val body = JSONObject()
        body.put("mobile_num",number)
        sendRequest(url,body, Request.Method.POST){ response ->
            if(response==null){
                onComplete(null)
                return@sendRequest
            }
            try {
                val id = response.getJSONObject("data")
                    .get("txn_id").toString()
                Log.d("id",id)
                onComplete(id)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
    private fun sendRequest(url:String, body:JSONObject, method:Int, onComplete:(response:JSONObject?)->Unit){

        val jsonObjectRequest = object : JsonObjectRequest(method, url, body,
            { response ->
                  onComplete(response)
            },{
                 error -> error.printStackTrace()
                onComplete(null)
                Log.e(tag,error.message.toString())
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }

        queue.add(jsonObjectRequest)
    }
    fun verifyOTP(id:String,otp:String,onComplete:(done:Boolean)->Unit){
        val url = Shop.baseUrl+Shop.Auth.verify
        val body = JSONObject()
        body.put("txn_id",id)
        body.put("token",otp)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, body,
            { response ->
                try {
                    val token = response.getString("token")
                    Log.e("token",token)
                    data.edit {
                        putString(Constants.token,token)
                        commit()
                    }
                    Constants.offlineToken = token
                    onComplete(true)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) {
                error -> error.printStackTrace()
            onComplete(false)
            Log.e(tag,id)
            Log.e(tag,otp)
            Log.e(tag,error.message.toString())
        }

        queue.add(jsonObjectRequest)
    }
}