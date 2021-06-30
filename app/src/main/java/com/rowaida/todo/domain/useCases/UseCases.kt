package com.rowaida.todo.domain.useCases

import com.rowaida.todo.domain.useCases.Note.AddNoteUseCase
import com.rowaida.todo.domain.useCases.Note.GetNotesUseCase
import com.rowaida.todo.domain.useCases.Note.RemoveNoteUseCase
import com.rowaida.todo.domain.useCases.Note.UpdateNoteUseCase
import com.rowaida.todo.domain.useCases.User.AddUserUseCase
import com.rowaida.todo.domain.useCases.User.CheckUserUseCase
import com.rowaida.todo.domain.useCases.User.GetUsernameUseCase

data class UseCases (
    val addNoteUseCase: AddNoteUseCase,
    val addUserUseCase: AddUserUseCase,
    val checkUserUseCase: CheckUserUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val getUsernameUseCase: GetUsernameUseCase,
    val removeNoteUseCase: RemoveNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
)

