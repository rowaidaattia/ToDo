package com.rowaida.todo.data.dataSource

import com.rowaida.todo.data.models.User

interface UserDataSource {

    fun add(user: User)

    fun check(user: User) : Boolean

}