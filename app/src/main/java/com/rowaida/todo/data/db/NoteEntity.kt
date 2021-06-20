package com.rowaida.todo.data.db

import androidx.room.*
import com.rowaida.todo.data.models.Note

@Entity(tableName = "Note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val note: String,
    val status: String
)