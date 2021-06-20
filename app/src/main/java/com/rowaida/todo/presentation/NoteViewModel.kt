package com.rowaida.todo.presentation

import android.app.Application
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.useCases.UseCases

class NoteViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addNote(note: Note) {
        interactors.addNoteUseCase(note)
    }

    fun getNotes(username: String) {
        interactors.getNotesUseCase(username)
    }

    fun removeNote(note: Note) {
        interactors.addNoteUseCase(note)
    }

    fun updateNote(note: Note) {
        interactors.addNoteUseCase(note)
    }

}