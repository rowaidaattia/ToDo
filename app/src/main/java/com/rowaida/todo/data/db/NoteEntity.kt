package com.rowaida.todo.data.db

import androidx.room.*
import com.rowaida.todo.data.models.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val note: String,
    @ColumnInfo val status: String
)