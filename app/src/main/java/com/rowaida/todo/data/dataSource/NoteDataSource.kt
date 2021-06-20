package com.rowaida.todo.data.dataSource

import com.rowaida.todo.data.models.Note

interface NoteDataSource {

    fun add(note: Note)

    fun remove(note: Note)

    fun update(note: Note)

    fun get(username: String) : List<Note>
}