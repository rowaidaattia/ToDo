package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.dataSource.UserDataSource
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.domain.repositoryInterface.UserRepository

class UserRepositoryImp (private val userDataSource: UserDataSource) :
    UserRepository {

    override suspend fun addUser(user: User) : Long = userDataSource.addUser(user)

    override suspend fun checkUser(usernameOrEmail: String, password: String) : Boolean =
        userDataSource.checkValidCredentials(usernameOrEmail, password)

    override suspend fun getUsername(usernameOrEmail: String): String =
        userDataSource.getUsername(usernameOrEmail)

    override suspend fun addSubAccount(admin: String, subAccount: String) =
        userDataSource.addSubAccount(admin, subAccount)

    override suspend fun getSubAccounts(username: String): List<String> =
        userDataSource.getSubAccounts(username)

    override suspend fun getAccountType(username: String) : AccountType =
        userDataSource.getAccountType(username)

    override suspend fun getAccounts(username: String) : List<String> =
        userDataSource.getAccounts(username)

}