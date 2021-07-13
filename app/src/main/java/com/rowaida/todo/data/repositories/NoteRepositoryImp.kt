package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.dataSource.NoteDataSource
import com.rowaida.todo.domain.repositoryInterface.NoteRepository

class NoteRepositoryImp (private val noteDataSource: NoteDataSource) :
    NoteRepository {

    override suspend fun addNote(note: Note) = noteDataSource.add(note)

    override suspend fun removeNote(note: Note) = noteDataSource.remove(note)

    override suspend fun updateNote(note: Note) = noteDataSource.update(note)

    override suspend fun getNotes(username: String) : List<Note> = noteDataSource.get(username)

    override suspend fun getAssignedNotes(username: String): List<Note> =
        noteDataSource.getAssignedNotes(username)

    override suspend fun getSubAccountsNotes(username: String): List<Note> =
        noteDataSource.getSubAccountsNotes(username)

    override fun deleteAllNotes(username: String) =
        noteDataSource.deleteAllNotes(username)

}