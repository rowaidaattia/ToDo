package com.rowaida.todo.presentation.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rowaida.todo.R
import com.rowaida.todo.utils.Constants
import com.rowaida.todo.utils.Navigation
import com.rowaida.todo.utils.SharedPreference


class MainActivity : AppCompatActivity() {

    private lateinit var loginButton : Button
    private lateinit var signupButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val login = SharedPreference(applicationContext).getValue("LOGIN")

        if (login != null) {
            val bundle = Bundle()
            bundle.putString(Constants.username, login)
            Navigation.goToActivity(bundle, this, NotesAdminActivity::class.java)

        }
        else {
            setContentView(R.layout.activity_main)
            initializeButtons()
        }
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
        Navigation.goToActivity(null, this, LoginActivity::class.java)
    }

    private fun goToSignup() {
        Navigation.goToActivity(null, this, SignupActivity::class.java)
    }
}