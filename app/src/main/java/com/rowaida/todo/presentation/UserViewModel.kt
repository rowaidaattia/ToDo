package com.rowaida.todo.presentation

import android.app.Application
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.domain.useCases.UseCases
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addUser(user: User) : Long {
        var r : Long = 1
        runBlocking {
            r = useCases.addUserUseCase(user)
        }
        return r
    }

    fun checkUser(usernameOrEmail: String, password: String) : Boolean {
        var r = false
        runBlocking {
            r = useCases.checkUserUseCase(usernameOrEmail, password)
        }
        return r

    }

    fun getUsername(usernameOrEmail: String) : String {
        var r = ""
        runBlocking {
            r = useCases.getUsernameUseCase(usernameOrEmail)
        }
        return r
    }

}