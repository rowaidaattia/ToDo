package com.rowaida.todo.domain.useCases

import com.rowaida.todo.data.repositories.NoteRepository
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.User

class RemoveNoteUseCase (private val noteRepository: NoteRepository) {

    operator fun invoke(user: User, note: Note) = noteRepository.removeNote(user, note)

}