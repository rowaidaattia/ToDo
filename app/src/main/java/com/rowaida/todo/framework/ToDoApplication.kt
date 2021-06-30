package com.rowaida.todo.framework

import android.app.Application
import com.rowaida.todo.data.db.*
import com.rowaida.todo.data.repositories.*
import com.rowaida.todo.domain.useCases.*
import com.rowaida.todo.domain.useCases.Note.AddNoteUseCase
import com.rowaida.todo.domain.useCases.Note.GetNotesUseCase
import com.rowaida.todo.domain.useCases.Note.RemoveNoteUseCase
import com.rowaida.todo.domain.useCases.Note.UpdateNoteUseCase
import com.rowaida.todo.domain.useCases.User.AddUserUseCase
import com.rowaida.todo.domain.useCases.User.CheckUserUseCase
import com.rowaida.todo.domain.useCases.User.GetUsernameUseCase

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