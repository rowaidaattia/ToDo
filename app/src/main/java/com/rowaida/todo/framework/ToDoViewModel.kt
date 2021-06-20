package com.rowaida.todo.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rowaida.todo.domain.useCases.UseCases

open class ToDoViewModel(application: Application, protected val interactors: UseCases) :
    AndroidViewModel(application) {

    protected val application: ToDoApplication = getApplication()

}