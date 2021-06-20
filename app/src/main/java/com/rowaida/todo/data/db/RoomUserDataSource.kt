package com.rowaida.todo.data.db

import android.content.Context
import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.dataSource.UserDataSource

class RoomUserDataSource (context: Context) : UserDataSource {

    private val userDao = ToDoDatabase.getInstance(context).userDao()

    override fun add(user: User) =
        userDao.addUser(UserEntity(
            user.username,
            user.password,
            user.gender.name,
            user.email,
            user.birthday.toString()
        ))

    override fun check(user: User): Boolean =
        userDao.checkUser(user.username, user.password)

}