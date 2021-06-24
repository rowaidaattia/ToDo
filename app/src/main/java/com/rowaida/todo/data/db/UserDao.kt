package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: UserEntity) : Long

    @Query("SELECT EXISTS(SELECT * FROM User WHERE " +
            "(username = :usernameOrEmail OR email = :usernameOrEmail) AND password = :password)")
    fun checkUser(usernameOrEmail: String, password: String) : Boolean

    @Query("SELECT username FROM User WHERE username = :usernameOrEmail OR email = :usernameOrEmail LIMIT 1")
    fun getUsername(usernameOrEmail: String) : String

}