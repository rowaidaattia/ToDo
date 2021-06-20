package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User
import com.rowaida.todo.domain.dataSource.NoteDataSource
import com.rowaida.todo.domain.repositoryInterface.NoteRepositoryInterface

class NoteRepository (private val noteDataSource: NoteDataSource) : NoteRepositoryInterface {

    override fun addNote(note: Note) = noteDataSource.add(note)

    override fun removeNote(note: Note) = noteDataSource.remove(note)

    override fun updateNote(note: Note) = noteDataSource.update(note)

    override fun getNotes(username: String) = noteDataSource.get(username)

}