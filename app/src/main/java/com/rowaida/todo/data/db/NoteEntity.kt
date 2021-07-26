package com.rowaida.todo.data.db

import androidx.room.*
import com.rowaida.todo.data.models.Note
import java.util.Date

@Entity(tableName = "Note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val name: String,
    val description: String,
    val status: String,
    val owner: String,
    val date: Date?
)