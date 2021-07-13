package com.rowaida.todo.domain.useCases

import com.rowaida.todo.domain.useCases.note.*
import com.rowaida.todo.domain.useCases.user.*

data class UseCases (
    val addNoteUseCase: AddNoteUseCase,
    val addSubAccountUseCase: AddSubAccountUseCase,
    val addUserUseCase: AddUserUseCase,
    val checkUserUseCase: CheckUserUseCase,
    val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    val getAccountsUseCase: GetAccountsUseCase,
    val getAccountTypeUseCase: GetAccountTypeUseCase,
    val getAssignedNotesUseCase: GetAssignedNotesUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val getSubAccountsNotesUseCase: GetSubAccountsNotesUseCase,
    val getSubAccountsUseCase: GetSubAccountsUseCase,
    val getUsernameUseCase: GetUsernameUseCase,
    val removeNoteUseCase: RemoveNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase
)

