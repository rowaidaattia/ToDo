package com.rowaida.todo.domain.dataSource

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface NoteDataSource {

    fun add(user: User, note: Note)

    fun remove(user: User, note: Note)

    fun update(user: User, note: Note)
}