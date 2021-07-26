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
                note.name,
                note.description,
                note.status.name,
                note.owner,
                note.date
        ))

    override suspend fun remove(note: Note) =
        noteDao.removeNote(
            NoteEntity(
                note.id,
                note.username,
                note.name,
                note.description,
                note.status.name,
                note.owner,
                note.date
            ))

    override suspend fun update(note: Note) =
        noteDao.updateNote(
            NoteEntity(
                note.id,
                note.username,
                note.name,
                note.description,
                note.status.name,
                note.owner,
                note.date
            ))

    override suspend fun get(username: String): List<Note> {
        var notes = mutableListOf<Note>()
        if (!userWithNotesDao.getNotes(username).isNullOrEmpty()) {
            userWithNotesDao.getNotes(username).forEach { userWithNotes ->
                notes.add(Note(
                    userWithNotes.note.id,
                    userWithNotes.note.username,
                    userWithNotes.note.name,
                    userWithNotes.note.description,
                    Status.valueOf(userWithNotes.note.status),
                    userWithNotes.note.owner,
                    userWithNotes.note.date
                ))
                userWithNotes.note
            }
        }
        println("GET NOTES AT DATA SOURCE: $notes")
        return notes
    }

    override suspend fun getAssignedNotes(username: String): List<Note> {
        var notes = mutableListOf<Note>()
        if (!userWithNotesDao.getAssignedNotes(username).isNullOrEmpty()) {
            userWithNotesDao.getAssignedNotes(username).forEach { userWithNotes ->
                notes.add(Note(
                    userWithNotes.note.id,
                    userWithNotes.note.username,
                    userWithNotes.note.name,
                    userWithNotes.note.description,
                    Status.valueOf(userWithNotes.note.status),
                    userWithNotes.note.owner,
                    userWithNotes.note.date
                ))
                userWithNotes.note
            }
        }
        println("ASSIGNED NOTES AT DATA SOURCE: $notes")
        return notes
    }

    override suspend fun getSubAccountsNotes(username: String): List<Note> {
        var notes = mutableListOf<Note>()
        if (!userWithNotesDao.getSubAccountsNotes(username).isNullOrEmpty()) {
            userWithNotesDao.getSubAccountsNotes(username).forEach { userWithNotes ->
                notes.add(Note(
                    userWithNotes.note.id,
                    userWithNotes.note.username,
                    userWithNotes.note.name,
                    userWithNotes.note.description,
                    Status.valueOf(userWithNotes.note.status),
                    userWithNotes.note.owner,
                    userWithNotes.note.date
                ))
                userWithNotes.note
            }
        }
        println("SUBACCOUNT NOTES AT DATA SOURCE: $notes")
        return notes
    }

    override fun deleteAllNotes(username: String) {
        noteDao.deleteAllNotes(username)
    }


}