package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun addUser(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username AND password = :password)")
    fun checkUser(username: String, password: String) : Boolean

}