package com.rowaida.todo.domain.useCases

data class UseCases (
    val addNoteUseCase: AddNoteUseCase,
    val addUserUseCase: AddUserUseCase,
    val checkUserUseCase: CheckUserUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val getUsernameUseCase: GetUsernameUseCase,
    val removeNoteUseCase: RemoveNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
)

