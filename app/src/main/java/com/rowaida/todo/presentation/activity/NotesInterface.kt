package com.rowaida.todo.presentation.activity

import com.rowaida.todo.data.models.Note

interface NotesInterface {

    fun updateFragment(item: Int?, notes: List<Note>)

    fun onEditNoteClicked(updateNote: Note)

}