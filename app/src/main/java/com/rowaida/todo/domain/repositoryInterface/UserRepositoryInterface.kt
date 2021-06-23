package com.rowaida.todo.domain.repositoryInterface

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface UserRepositoryInterface {

    suspend fun addUser(user: User)

    suspend fun checkUser(usernameOrEmail: String, password: String) : Boolean

}