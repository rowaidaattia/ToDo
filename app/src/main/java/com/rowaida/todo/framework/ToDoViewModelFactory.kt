package com.rowaida.todo.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rowaida.todo.useCases.UseCases

object ToDoViewModelFactory : ViewModelProvider.Factory {

    lateinit var application: Application

    lateinit var dependencies: UseCases

    fun inject(application: Application, dependencies: UseCases) {
        ToDoViewModelFactory.application = application
        ToDoViewModelFactory.dependencies = dependencies
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(ToDoViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java, UseCases::class.java)
                .newInstance(
                    application,
                    dependencies)
        } else {
            throw IllegalStateException("ViewModel must extend MajesticViewModel")
        }
    }

}