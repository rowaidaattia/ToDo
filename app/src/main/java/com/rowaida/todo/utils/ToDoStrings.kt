package com.rowaida.todo.utils

import androidx.annotation.StringRes
import com.rowaida.todo.framework.ToDoApplication

object ToDoStrings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return ToDoApplication.instance.getString(stringRes, *formatArgs)
    }
}