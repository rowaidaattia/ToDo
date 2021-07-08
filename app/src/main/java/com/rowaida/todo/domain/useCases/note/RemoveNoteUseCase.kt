package com.rowaida.todo.domain.useCases.note

import com.rowaida.todo.data.repositories.NoteRepository
import com.rowaida.todo.data.models.Note

class RemoveNoteUseCase (private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)

}