package com.rowaida.todo.presentation

import android.app.Application
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.useCases.UseCases

class UserViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addUser(user: User) {
        interactors.addUserUseCase(user)
    }

    fun checkUser(user: User) {
        interactors.checkUserUseCase(user)
    }

}