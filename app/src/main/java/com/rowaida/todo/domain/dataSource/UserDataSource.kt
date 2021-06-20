package com.rowaida.todo.domain.dataSource

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface UserDataSource {

    fun add(user: User)

    fun check(user: User) : Boolean

}