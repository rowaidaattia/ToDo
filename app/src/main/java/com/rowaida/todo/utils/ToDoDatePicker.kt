package com.rowaida.todo.utils

import android.content.Context
import com.google.android.material.datepicker.MaterialDatePicker
import com.rowaida.todo.R

object ToDoDatePicker {

    //FIXME select date is hardcoded
    fun setDatePicker(applicationContext: Context) : MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(applicationContext.getString(R.string.select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
}