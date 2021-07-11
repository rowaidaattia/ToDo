package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserWithNotesDao {

    //@RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT * FROM Note WHERE username = :username AND owner = :username")
    fun getNotes(username: String): List<UserWithNotes>

    @Transaction
    @Query("SELECT * FROM Note WHERE username = :username AND owner <> :username")
    fun getAssignedNotes(username: String): List<UserWithNotes>

    @Transaction
    @Query("SELECT * FROM Note WHERE owner = :username AND username <> :username")
    fun getSubAccountsNotes(username: String): List<UserWithNotes>


}