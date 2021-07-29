package com.rowaida.todo.data.dataSource

import com.rowaida.todo.data.models.Note

interface NoteDataSource {

    suspend fun addNote(note: Note)

    suspend fun removeNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun getUserNotes(username: String) : List<Note>

    suspend fun getAssignedNotes(username: String) : List<Note>

    suspend fun getSubAccountsNotes(username: String) : List<Note>

    fun deleteAllNotes(username: String)
}