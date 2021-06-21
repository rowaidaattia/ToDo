package com.rowaida.todo.domain.useCases

import com.rowaida.todo.data.repositories.NoteRepository
import com.rowaida.todo.data.models.Note

class RemoveNoteUseCase (private val noteRepository: NoteRepository) {

    operator fun invoke(note: Note) = noteRepository.removeNote(note)

}