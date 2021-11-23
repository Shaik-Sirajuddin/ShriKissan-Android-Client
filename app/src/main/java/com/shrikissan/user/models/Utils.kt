package com.shrikissan.user.models

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

fun Activity.showToast(text:String){
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}

fun Activity.isConnected():Boolean{
    val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->    true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->   true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->   true
            else -> false
        }
    }
    else {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

class Constants{
    companion object{
        var phoneNumber = ""
    }
}