package com.rowaida.todo.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rowaida.todo.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToLogin(v: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun goToSignup(v: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }
}