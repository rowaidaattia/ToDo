package com.rowaida.todo.useCases

data class UseCases (
    val addNoteUseCase: AddNoteUseCase,
    val addUserUseCase: AddUserUseCase,
    val checkUserUseCase: CheckUserUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val removeNoteUseCase: RemoveNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
)

