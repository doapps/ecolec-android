package com.ecolec.cliente.retrofit

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class ConvertRetrofit {
    companion object {
        fun isConnected(activity: Activity): Boolean {
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager!!.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
    }
}