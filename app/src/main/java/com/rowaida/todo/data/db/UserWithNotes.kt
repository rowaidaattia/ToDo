package com.rowaida.todo.data.db

import androidx.room.*

data class UserWithNotes(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val notes: List<NoteEntity>
)