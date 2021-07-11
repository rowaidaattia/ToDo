package com.rowaida.todo.presentation.viewModel

import android.app.Application
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.domain.useCases.UseCases
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class NoteViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addNote(note: Note) {
        GlobalScope.launch {
            useCases.addNoteUseCase(note)
        }
    }

    suspend fun getNotes(username: String) : List<Note> {
        var notes = GlobalScope.async {
            useCases.getNotesUseCase(username)
        }
        return notes.await()

    }

    suspend fun getAssignedNotes(username: String) : List<Note> {
        var notes = GlobalScope.async {
            useCases.getAssignedNotesUseCase(username)
        }
        return notes.await()

    }

    suspend fun getSubAccountsNotes(username: String) : List<Note> {
        var notes = GlobalScope.async {
            useCases.getSubAccountsNotesUseCase(username)
        }
        return notes.await()

    }

    fun removeNote(note: Note) {
        GlobalScope.launch {
            useCases.removeNoteUseCase(note)
        }

    }

    fun updateNote(note: Note) {
        GlobalScope.launch {
            useCases.updateNoteUseCase(note)
        }

    }

}