package com.rowaida.todo.presentation.viewModel

import android.app.Application
import android.content.Intent
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.domain.useCases.UseCases
import com.rowaida.todo.presentation.service.ToDoService
import com.rowaida.todo.utils.ToDoConstants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//FIXME fix warnings
class NoteViewModel(application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addNote(note: Note) {
        GlobalScope.launch {
            useCases.addNoteUseCase(note)
        }
    }

    suspend fun getNotes(username: String): List<Note> {
        val notes = GlobalScope.async {
            useCases.getNotesUseCase(username)
        }
        return notes.await()

    }

    suspend fun getAssignedNotes(username: String): List<Note> {
        val notes = GlobalScope.async {
            useCases.getAssignedNotesUseCase(username)
        }
        return notes.await()

    }

    suspend fun getSubAccountsNotes(username: String): List<Note> {
        val notes = GlobalScope.async {
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

    //FIXME remove commented code
    fun deleteAllNotes(username: String) {
        Intent(application, ToDoService::class.java)
            .also { intent ->
                intent.putExtra(ToDoConstants.username, username)
                application.startService(intent)
            }
    }

}