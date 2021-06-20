package com.rowaida.todo.domain.repositoryInterface

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface NoteRepositoryInterface {

    fun addNote(note: Note)

    fun removeNote(note: Note)

    fun updateNote(note: Note)

    fun getNotes(username: String) : List<Note>

}