package com.rowaida.todo.domain.repositoryInterface

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface UserRepositoryInterface {

    fun addUser(user: User)

    fun getNotes(user: User) : List<Note>

}