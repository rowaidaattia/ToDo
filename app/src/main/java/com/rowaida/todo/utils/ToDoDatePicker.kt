package com.rowaida.todo.utils

import com.google.android.material.datepicker.MaterialDatePicker

object ToDoDatePicker {

    fun setDatePicker() : MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
}