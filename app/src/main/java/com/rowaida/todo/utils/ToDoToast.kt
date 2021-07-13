package com.rowaida.todo.utils

import android.content.Context
import android.widget.Toast

object ToDoToast {

    fun toast(applicationContext: Context, text: String) {
        Toast.makeText(applicationContext,
            text,
            Toast.LENGTH_LONG).show()
    }

}