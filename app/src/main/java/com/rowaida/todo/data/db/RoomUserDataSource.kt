package com.rowaida.todo.data.db

import android.content.Context
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User
import com.rowaida.todo.domain.dataSource.UserDataSource

class RoomUserDataSource (context: Context) : UserDataSource {

    private val userDao = ToDoDatabase.getInstance(context).userDao()

    override fun add(user: User) =
        userDao.insertUser(UserEntity(
            user.username,
            user.password,
            null
        ))

    override fun get(user: User): List<Note> {
        TODO("Not yet implemented")
        return emptyList()
    }

}