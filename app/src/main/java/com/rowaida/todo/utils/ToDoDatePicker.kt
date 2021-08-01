package com.rowaida.todo.utils

import com.google.android.material.datepicker.MaterialDatePicker

object ToDoDatePicker {

    //FIXME select date is hardcoded
    fun setDatePicker() : MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
}