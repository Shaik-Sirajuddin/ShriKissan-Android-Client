package com.shrikissan.user.network

import android.content.Context

class Repository(context: Context){
    val queue = MySingleton.getInstance(context.applicationContext).requestQueue

    fun getCategories(){

    }
}