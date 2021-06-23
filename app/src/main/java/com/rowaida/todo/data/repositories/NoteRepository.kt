package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.dataSource.NoteDataSource
import com.rowaida.todo.domain.repositoryInterface.NoteRepositoryInterface

class NoteRepository (private val noteDataSource: NoteDataSource) : NoteRepositoryInterface {

    override suspend fun addNote(note: Note) = noteDataSource.add(note)

    override suspend fun removeNote(note: Note) = noteDataSource.remove(note)

    override suspend fun updateNote(note: Note) = noteDataSource.update(note)

    override suspend fun getNotes(username: String) : List<Note> = noteDataSource.get(username)

}