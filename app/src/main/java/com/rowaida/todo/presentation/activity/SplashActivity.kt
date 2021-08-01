package com.rowaida.todo.presentation.activity

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.presentation.broadcastReceiver.NetworkBroadcastReceiver
import com.rowaida.todo.utils.ToDoConstants
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoSharedPreference

class SplashActivity : AppCompatActivity() {

    private val splashScreenTimeOut = 2000
    private lateinit var br : BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        br = NetworkBroadcastReceiver()
        //FIXME try not to use deprecated code, you can create a style with no status bar and App bar and add it to the activity
        //This method is used so that your splash activity
        //can cover the entire screen.
        setContentView(R.layout.activity_splash)
        //this will bind your SplashActivity.class file with activity_splash.
        Handler().postDelayed({

            //FIXME register on onResume and unregister
            setBroadcast()

            goToAnotherActivity()

            finish()

        }, splashScreenTimeOut.toLong())
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(br)
    }

    override fun onResume() {
        super.onResume()
        setBroadcast()
    }

    private fun setBroadcast() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
        }
        registerReceiver(br, filter)

    }

    private fun goToAnotherActivity() {
        val login = ToDoSharedPreference(applicationContext).getValue(ToDoConstants.login, null)
        val accountType = ToDoSharedPreference(applicationContext).getValue(ToDoConstants.accountType, null)

        if (login != null) {
            val bundle = Bundle()
            bundle.putString(ToDoConstants.username, login)
            if (AccountType.ADMIN == accountType?.let { AccountType.valueOf(it) }) {
                ToDoNavigation.goToActivity(bundle, this, NotesAdminActivity::class.java)
            }
            else {
                ToDoNavigation.goToActivity(bundle, this, NotesSubAccountActivity::class.java)
            }

        }
        else {
            ToDoNavigation.goToActivity(context = this, activityClass = LoginActivity::class.java)
        }
    }

}

