package com.rowaida.todo.data.dataSource

import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.data.models.User

interface UserDataSource {

    suspend fun add(user: User) : Long

    suspend fun check(usernameOrEmail: String, password: String) : Boolean

    suspend fun get(usernameOrEmail: String) : String

    suspend fun addSubAccount(admin: String, subAccount: String)

    suspend fun getSubAccounts(username: String) : List<String>

    suspend fun getAccountType(username: String) : AccountType

    suspend fun getAccounts(username: String) : List<String>

}