package com.rowaida.todo.data.dataSource

import com.rowaida.todo.data.models.User

interface UserDataSource {

    suspend fun add(user: User) : Long

    suspend fun check(usernameOrEmail: String, password: String) : Boolean

    suspend fun get(usernameOrEmail: String) : String

}