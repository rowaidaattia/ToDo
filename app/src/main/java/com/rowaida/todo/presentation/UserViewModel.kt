package com.rowaida.todo.presentation

import android.app.Application
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.domain.useCases.UseCases

class UserViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addUser(user: User) {
        useCases.addUserUseCase(user)
    }

    fun checkUser(user: User) {
        useCases.checkUserUseCase(user)
    }

}