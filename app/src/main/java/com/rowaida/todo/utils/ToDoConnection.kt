package com.rowaida.todo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.rowaida.todo.data.models.NetworkState

//FIXME it is a duplicated method you can create it as utils or as extension
//FIXME deprecate code
object ToDoConnection {
    fun getConnectivityStatus(context: Context): NetworkState {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == NetworkCapabilities.TRANSPORT_WIFI) return NetworkState.WIFI
            if (activeNetwork.type == NetworkCapabilities.TRANSPORT_CELLULAR) return NetworkState.MOBILE
        }
        return NetworkState.NO_INTERNET

    }
}