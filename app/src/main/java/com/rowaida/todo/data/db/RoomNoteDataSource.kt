package com.rowaida.todo.data.db

import android.content.Context
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.data.dataSource.NoteDataSource

class RoomNoteDataSource (context: Context) : NoteDataSource {

    private val noteDao = ToDoDatabase.getInstance(context).noteDao()
    private val userWithNotesDao = ToDoDatabase.getInstance(context).userWithNotesDao()

    override suspend fun add(note: Note) =
        noteDao.addNote(
            NoteEntity(
            note.id,
            note.username,
            note.note,
            note.status.name
        ))

    override suspend fun remove(note: Note) =
        noteDao.removeNote(
            NoteEntity(
                note.id,
                note.username,
                note.note,
                note.status.name
            ))

    override suspend fun update(note: Note) =
        noteDao.updateNote(
            NoteEntity(
                note.id,
                note.username,
                note.note,
                note.status.name
            ))

    override suspend fun get(username: String): List<Note> {
        var notes = listOf<Note>()
        if (!userWithNotesDao.getNotes(username).isNullOrEmpty()) {
            notes = userWithNotesDao.getNotes(username).first().notes.map {
                Note(
                    it.id,
                    it.username,
                    it.note,
                    Status.valueOf(it.status)
                )
            }
        }
//        else {
//            println("HERE: NOTES IS EMPTY")
//            //return mutableListOf()
//        }
//        println("HERE: NOTES IS NOT EMPTY $notes")
        return notes
    }

}