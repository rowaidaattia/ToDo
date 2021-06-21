package com.rowaida.todo.presentation

import android.app.Application
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.domain.useCases.UseCases

class NoteViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addNote(note: Note) {
        useCases.addNoteUseCase(note)
    }

    fun getNotes(username: String) {
        useCases.getNotesUseCase(username)
    }

    fun removeNote(note: Note) {
        useCases.addNoteUseCase(note)
    }

    fun updateNote(note: Note) {
        useCases.addNoteUseCase(note)
    }

}