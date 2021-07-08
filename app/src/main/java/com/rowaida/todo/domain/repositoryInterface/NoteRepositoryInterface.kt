package com.rowaida.todo.domain.repositoryInterface

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

interface NoteRepositoryInterface {

    suspend fun addNote(note: Note)

    suspend fun removeNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun getNotes(username: String) : List<Note>

    suspend fun getAssignedNotes(username: String) : List<Note>

    suspend fun getSubAccountsNotes(username: String) : List<Note>

}