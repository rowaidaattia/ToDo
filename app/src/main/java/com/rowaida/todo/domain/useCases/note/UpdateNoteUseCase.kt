package com.rowaida.todo.domain.useCases.note

import com.rowaida.todo.data.repositories.NoteRepositoryImp
import com.rowaida.todo.data.models.Note

class UpdateNoteUseCase (private val noteRepository: NoteRepositoryImp) {

    suspend operator fun invoke(note: Note) = noteRepository.updateNote(note)

}