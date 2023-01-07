package com.shrikissan.user.models

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.isConnected(): Boolean {
    val connectivityManager: ConnectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

class Constants {
    companion object {
        var phoneNumber = ""
        const val appId:String = ""
        const val preference = "com.shrikissan.user"
        const val userNumber = "number"
        const val isLoggedIn = "login"
        const val language = "language"
        const val token = "token"
        const val orderKey = "items"
        var offlineToken = ""
        var orderList = ArrayList<CartItem>()
        var number:String = ""
        var name:String = ""
    }
}
class FireConsts{
    companion object{
        const val Support = "Support"
        const val VoiceRequest = "VoiceCallRequests"
        const val VideoRequest = "VideoCallRequests"
        const val VoiceType = "VoiceCall"
        const val VideoType = "VideoCall"
        const val soilRequest = "SoilTestRequests"
        const val soilType = "SoilTest"
    }
}