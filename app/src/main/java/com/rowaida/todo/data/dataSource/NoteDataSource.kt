package com.rowaida.todo.data.dataSource

import com.rowaida.todo.data.models.Note

interface NoteDataSource {

    suspend fun add(note: Note)

    suspend fun remove(note: Note)

    suspend fun update(note: Note)

    suspend fun get(username: String) : List<Note>

    suspend fun getAssignedNotes(username: String) : List<Note>

    suspend fun getSubAccountsNotes(username: String) : List<Note>
}