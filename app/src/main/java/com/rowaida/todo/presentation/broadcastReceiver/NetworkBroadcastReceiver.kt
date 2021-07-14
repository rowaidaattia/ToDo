package com.rowaida.todo.presentation.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.rowaida.todo.data.models.NetworkState
import com.rowaida.todo.utils.ToDoToast


private const val TAG = "MyBroadcastReceiver"

class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //println("CONNECTION STATUS: " + getConnectivityStatus(context))
        when (val networkState = getConnectivityStatus(context)) {
            NetworkState.NO_INTERNET ->
                startActivity(context, Intent(Settings.ACTION_SETTINGS), null)
            else ->
                ToDoToast.toast(context, "Current Network State: $networkState")
        }
    }

    private fun getConnectivityStatus(context: Context): NetworkState {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == TYPE_WIFI) return NetworkState.WIFI
            if (activeNetwork.type == TYPE_MOBILE) return NetworkState.MOBILE
        }
        return NetworkState.NO_INTERNET
    }
}