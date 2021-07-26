package com.rowaida.todo.presentation.activity

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.presentation.broadcastReceiver.NetworkBroadcastReceiver
import com.rowaida.todo.utils.ToDoConstants
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoSharedPreference


class MainActivity : AppCompatActivity() {

    private lateinit var loginButton : Button
    private lateinit var signupButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val br: BroadcastReceiver = NetworkBroadcastReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
        }
        registerReceiver(br, filter)

        setContentView(R.layout.activity_main)
        initializeButtons()
    }

    private fun initializeButtons() {
        loginButton = findViewById(R.id.login_button)
        signupButton = findViewById(R.id.signup_button)

        loginButton.setOnClickListener {
            goToLogin()
        }

        signupButton.setOnClickListener {
            goToSignup()
        }
    }

    private fun goToLogin() {
        ToDoNavigation.goToActivity(null, this, LoginActivity::class.java)
    }

    private fun goToSignup() {
        ToDoNavigation.goToActivity(null, this, SignupActivity::class.java)
    }
}