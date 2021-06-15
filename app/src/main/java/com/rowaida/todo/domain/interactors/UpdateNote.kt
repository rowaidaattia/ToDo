package com.rowaida.todo.domain.interactors

import com.rowaida.todo.data.NoteRepository
import com.rowaida.todo.data.modules.Note
import com.rowaida.todo.data.modules.User

class UpdateNote (private val noteRepository: NoteRepository) {

    operator fun invoke(user: User, note: Note) = noteRepository.updateNote(user, note)

}