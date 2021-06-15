package com.rowaida.todo.data.repositories

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User
import com.rowaida.todo.domain.dataSource.NoteDataSource
import com.rowaida.todo.domain.repositoryInterface.NoteRepositoryInterface

class NoteRepository (private val noteDataSource: NoteDataSource) : NoteRepositoryInterface {

    override fun addNote(user: User, note: Note) = noteDataSource.add(user, note)

    override fun removeNote(user: User, note: Note) = noteDataSource.remove(user, note)

    override fun updateNote(user: User, note: Note) = noteDataSource.update(user, note)

}