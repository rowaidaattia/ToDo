package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun addNote(note: NoteEntity)

    @Delete
    fun removeNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

}