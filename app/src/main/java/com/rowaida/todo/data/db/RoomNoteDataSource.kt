package com.rowaida.todo.data.db

import android.content.Context
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.data.dataSource.NoteDataSource

class RoomNoteDataSource (context: Context) : NoteDataSource {

    private val noteDao = ToDoDatabase.getInstance(context).noteDao()
    private val userWithNotesDao = ToDoDatabase.getInstance(context).userWithNotesDao()

    override fun add(note: Note) =
        noteDao.addNote(
            NoteEntity(
            note.id,
            note.username,
            note.note,
            note.status.name
        ))

    override fun remove(note: Note) =
        noteDao.removeNote(
            NoteEntity(
                note.id,
                note.username,
                note.note,
                note.status.name
            ))

    override fun update(note: Note) =
        noteDao.updateNote(
            NoteEntity(
                note.id,
                note.username,
                note.note,
                note.status.name
            ))

    override fun get(username: String) =
        userWithNotesDao.getNotes(username).map {
            Note(
                it.id,
                it.username,
                it.note,
                Status.valueOf(it.status)
            )
        }


}