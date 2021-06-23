package com.rowaida.todo.data.db

import android.content.Context
import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.dataSource.UserDataSource

class RoomUserDataSource (context: Context) : UserDataSource {

    private val userDao = ToDoDatabase.getInstance(context).userDao()

    override suspend fun add(user: User) =
        userDao.addUser(UserEntity(
            user.username,
            user.password,
            user.gender.name,
            user.email,
            user.birthday.toString()
        ))

    override suspend fun check(usernameOrEmail: String, password: String): Boolean =
        userDao.checkUser(usernameOrEmail, password)

}