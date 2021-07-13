package com.rowaida.todo.domain.useCases.note

import com.rowaida.todo.domain.repositoryInterface.NoteRepository

class DeleteAllNotesUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(username: String) = noteRepository.deleteAllNotes(username)
}