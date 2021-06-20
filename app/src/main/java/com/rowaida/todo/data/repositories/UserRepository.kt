package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.User
import com.rowaida.todo.domain.dataSource.UserDataSource
import com.rowaida.todo.domain.repositoryInterface.UserRepositoryInterface

class UserRepository (private val userDataSource: UserDataSource) : UserRepositoryInterface {

    override fun addUser(user: User) = userDataSource.add(user)

    override fun checkUser(user: User) = userDataSource.check(user)

}