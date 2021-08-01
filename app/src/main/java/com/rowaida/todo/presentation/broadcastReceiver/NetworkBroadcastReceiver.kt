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
import com.rowaida.todo.utils.ToDoConnection
import com.rowaida.todo.utils.ToDoToast


private const val TAG = "MyBroadcastReceiver"

class NetworkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (val networkState = ToDoConnection.getConnectivityStatus(context)) {
            NetworkState.NO_INTERNET ->
                startActivity(context, Intent(Settings.ACTION_SETTINGS), null)
            else ->
                ToDoToast.toast(context, "Current Network State: $networkState")
        }
    }
}