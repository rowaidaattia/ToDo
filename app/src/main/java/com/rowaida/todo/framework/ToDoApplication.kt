package com.rowaida.todo.framework

import android.app.Application
import com.rowaida.todo.data.db.*
import com.rowaida.todo.data.repositories.*
import com.rowaida.todo.domain.useCases.*

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val userRepository = UserRepository(RoomUserDataSource(this))
        val noteRepository = NoteRepository(RoomNoteDataSource(this))

        ToDoViewModelFactory.inject(
            this,
            UseCases(
                AddNoteUseCase(noteRepository),
                AddUserUseCase(userRepository),
                CheckUserUseCase(userRepository),
                GetNotesUseCase(noteRepository),
                GetUsernameUseCase(userRepository),
                RemoveNoteUseCase(noteRepository),
                UpdateNoteUseCase(noteRepository)
            )
        )

    }

}