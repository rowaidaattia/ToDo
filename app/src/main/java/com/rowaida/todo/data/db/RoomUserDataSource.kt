package com.rowaida.todo.data.db

import android.content.Context
import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.dataSource.UserDataSource
import com.rowaida.todo.data.models.AccountType

class RoomUserDataSource (context: Context) : UserDataSource {

    private val userDao = ToDoDatabase.getInstance(context).userDao()

    override suspend fun addUser(user: User) : Long =
        userDao.addUser(UserEntity(
            user.username,
            user.password,
            user.gender.name,
            user.email,
            user.birthday.toString(),
            user.admin,
            user.accountType.toString()
        ))

    override suspend fun checkValidCredentials(usernameOrEmail: String, password: String): Boolean =
        userDao.checkValidCredentials(usernameOrEmail, password)

    override suspend fun getUsername(usernameOrEmail: String): String =
        userDao.getUsername(usernameOrEmail)

    override suspend fun addSubAccount(admin: String, subAccount: String) =
        userDao.addSubAccount(admin, subAccount)

    override suspend fun getSubAccounts(username: String): List<String> =
        userDao.getSubAccounts(username)

    override suspend fun getAccountType(username: String): AccountType =
        AccountType.valueOf(userDao.getAccountType(username))

    override suspend fun getAccounts(username: String): List<String> =
        userDao.getAccounts(username)

}