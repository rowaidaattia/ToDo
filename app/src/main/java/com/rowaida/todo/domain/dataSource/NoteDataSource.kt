package com.rowaida.todo.domain.dataSource

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface NoteDataSource {

    fun add(note: Note)

    fun remove(note: Note)

    fun update(note: Note)

    fun get(username: String) : List<Note>
}