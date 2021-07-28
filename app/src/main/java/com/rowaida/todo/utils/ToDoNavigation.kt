package com.rowaida.todo.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle

object ToDoNavigation {

    //FIXME you can make bundle default value to null so you don't need to pass null every time you call the method
    fun goToActivity(bundle: Bundle?, context: Context, activityClass: Class<*>) {
        val intent = Intent(context, activityClass)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        context.startActivity(intent)
    }

    fun goToNotes(username: String, accountType: String, context: Context, notesActivity: Class<*>) {
        ToDoSharedPreference(context).writeString(ToDoConstants.login, username)
        ToDoSharedPreference(context).writeString(ToDoConstants.accountType, accountType)
        val bundle = Bundle()
        bundle.putString(ToDoConstants.username, username)
        goToActivity(bundle, context, notesActivity)
    }

}