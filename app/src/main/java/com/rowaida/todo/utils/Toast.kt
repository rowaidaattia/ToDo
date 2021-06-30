package com.rowaida.todo.utils

import android.content.Context
import android.widget.Toast

object Toast {

    fun toast(applicationContext: Context, text: String) {
        Toast.makeText(applicationContext,
            text,
            Toast.LENGTH_LONG).show()
    }

}