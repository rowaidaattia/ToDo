package com.rowaida.todo.data

import com.rowaida.todo.data.modules.Note
import com.rowaida.todo.data.modules.User
import com.rowaida.todo.domain.NoteDataSource

class NoteRepository (private val noteDataSource: NoteDataSource) {

    fun addNote(user: User, note: Note) = noteDataSource.add(user, note)

    fun removeNote(user: User, note: Note) = noteDataSource.remove(user, note)

    fun updateNote(user: User, note: Note) = noteDataSource.update(user, note)

}