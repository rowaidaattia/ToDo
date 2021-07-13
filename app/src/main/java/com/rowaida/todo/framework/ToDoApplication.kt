package com.rowaida.todo.framework

import android.app.Application
import com.rowaida.todo.data.db.*
import com.rowaida.todo.data.repositories.*
import com.rowaida.todo.domain.useCases.*
import com.rowaida.todo.domain.useCases.note.*
import com.rowaida.todo.domain.useCases.user.*

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val userRepository = UserRepositoryImp(RoomUserDataSource(this))
        val noteRepository = NoteRepositoryImp(RoomNoteDataSource(this))

        ToDoViewModelFactory.inject(
            this,
            UseCases(
                AddNoteUseCase(noteRepository),
                AddSubAccountUseCase(userRepository),
                AddUserUseCase(userRepository),
                CheckUserUseCase(userRepository),
                DeleteAllNotesUseCase(noteRepository),
                GetAccountsUseCase(userRepository),
                GetAccountTypeUseCase(userRepository),
                GetAssignedNotesUseCase(noteRepository),
                GetNotesUseCase(noteRepository),
                GetSubAccountsNotesUseCase(noteRepository),
                GetSubAccountsUseCase(userRepository),
                GetUsernameUseCase(userRepository),
                RemoveNoteUseCase(noteRepository),
                UpdateNoteUseCase(noteRepository)
            )
        )

    }

}