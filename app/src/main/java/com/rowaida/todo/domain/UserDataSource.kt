package com.rowaida.todo.domain

import com.rowaida.todo.data.modules.Note
import com.rowaida.todo.data.modules.User

interface UserDataSource {

    fun add(user: User) : Boolean

    fun get(user: User) : List<Note>

}