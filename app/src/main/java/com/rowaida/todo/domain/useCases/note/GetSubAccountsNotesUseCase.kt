package com.rowaida.todo.domain.useCases.note

import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.repositories.NoteRepository

class GetSubAccountsNotesUseCase (private val noteRepository: NoteRepository) {

    suspend operator fun invoke(username: String) : List<Note> =
        noteRepository.getSubAccountsNotes(username)

}