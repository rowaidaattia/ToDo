package com.rowaida.todo.domain

import com.rowaida.todo.data.modules.Note
import com.rowaida.todo.data.modules.User

interface NoteDataSource {

    fun add(user: User, note: Note)

    fun remove(user: User, note: Note)

    fun update(user: User, note: Note)
}