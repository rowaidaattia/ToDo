package com.rowaida.todo.data.db

import androidx.room.*

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey val username: String,
    val password: String
)