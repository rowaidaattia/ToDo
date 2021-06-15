package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserEntity)

//    @Query("SELECT * FROM user WHERE username LIKE :username")
//    fun getUser(username: String): UserDao

}