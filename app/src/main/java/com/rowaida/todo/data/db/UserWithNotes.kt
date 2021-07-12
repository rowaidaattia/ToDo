package com.rowaida.todo.data.db

import androidx.room.*


data class UserWithNotes(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "username",
        entity = UserEntity::class,
        entityColumn = "username"
    )
    val user: UserEntity
)