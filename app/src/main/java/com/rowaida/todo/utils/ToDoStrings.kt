package com.rowaida.todo.utils

import android.content.Context
import androidx.annotation.StringRes
import com.rowaida.todo.framework.ToDoApplication

fun Context.getString(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
    return getString(stringRes, *formatArgs)
}