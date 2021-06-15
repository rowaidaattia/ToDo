package com.rowaida.todo.domain.repositoryInterface

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface NoteRepositoryInterface {

    fun addNote(user: User, note: Note)

    fun removeNote(user: User, note: Note)

    fun updateNote(user: User, note: Note)

}