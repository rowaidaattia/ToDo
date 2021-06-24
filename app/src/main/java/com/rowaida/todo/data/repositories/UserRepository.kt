package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.dataSource.UserDataSource
import com.rowaida.todo.domain.repositoryInterface.UserRepositoryInterface

class UserRepository (private val userDataSource: UserDataSource) : UserRepositoryInterface {

    override suspend fun addUser(user: User) : Long = userDataSource.add(user)

    override suspend fun checkUser(usernameOrEmail: String, password: String) : Boolean =
        userDataSource.check(usernameOrEmail, password)

    override suspend fun getUsername(usernameOrEmail: String): String =
        userDataSource.get(usernameOrEmail)


}