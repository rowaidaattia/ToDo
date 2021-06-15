package com.rowaida.todo.data

import com.rowaida.todo.data.modules.User
import com.rowaida.todo.domain.UserDataSource

class UserRepository (private val userDataSource: UserDataSource) {

    fun addUser(user: User) = userDataSource.add(user)

    fun getNotes(user: User) = userDataSource.get(user)
}