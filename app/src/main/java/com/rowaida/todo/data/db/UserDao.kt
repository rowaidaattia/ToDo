package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun addUser(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM User WHERE " +
            "(username = :usernameOrEmail OR email = :usernameOrEmail) AND password = :password)")
    fun checkUser(usernameOrEmail: String, password: String) : Boolean

}