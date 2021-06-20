package com.rowaida.todo.useCases

import com.rowaida.todo.data.repositories.NoteRepository

class GetNotesUseCase (private val noteRepository: NoteRepository) {

    operator fun invoke(username: String) = noteRepository.getNotes(username)

}