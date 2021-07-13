package com.rowaida.todo.utils

import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

object ToDoAlert {

    fun dialog(context: Context, editText: EditText, message: String) {
        val alert = AlertDialog.Builder(context)
        alert.setMessage(message)
        alert.setView(editText)
    }
}