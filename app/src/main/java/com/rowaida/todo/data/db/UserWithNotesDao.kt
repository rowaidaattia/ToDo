package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserWithNotesDao {

    @Transaction
    @Query("SELECT * FROM Note WHERE username LIKE :usernameOrEmail")
    fun getNotes(usernameOrEmail: String): List<UserWithNotes>

}