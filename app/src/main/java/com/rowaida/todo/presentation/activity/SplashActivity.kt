package com.rowaida.todo.presentation.activity

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.presentation.broadcastReceiver.NetworkBroadcastReceiver
import com.rowaida.todo.utils.ToDoConstants
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoSharedPreference

class SplashActivity : AppCompatActivity() {

    private val splashScreenTimeOut = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //This method is used so that your splash activity
        //can cover the entire screen.
        setContentView(R.layout.activity_splash)
        //this will bind your SplashActivity.class file with activity_splash.

        Handler().postDelayed({

            setBroadcast()

            goToAnotherActivity()

            finish()

        }, splashScreenTimeOut.toLong())
    }
    private fun setBroadcast() {
        val br: BroadcastReceiver = NetworkBroadcastReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
        }
        registerReceiver(br, filter)

    }

    private fun goToAnotherActivity() {
        val login = ToDoSharedPreference(applicationContext).getValue(ToDoConstants.login)
        val accountType = ToDoSharedPreference(applicationContext).getValue(ToDoConstants.accountType)

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

