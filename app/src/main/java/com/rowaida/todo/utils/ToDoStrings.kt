package com.rowaida.todo.utils

import androidx.annotation.StringRes
import com.rowaida.todo.framework.ToDoApplication

object ToDoStrings {
    //FIXME you can create this as extension to Context class let's discuss
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return ToDoApplication.instance.getString(stringRes, *formatArgs)
    }
}