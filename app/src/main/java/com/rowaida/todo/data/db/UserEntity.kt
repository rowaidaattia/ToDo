package com.rowaida.todo.data.db

import androidx.room.*

@Entity(tableName = "User", primaryKeys = ["username", "email"])
data class UserEntity(
    val username: String,
    val password: String,
    val gender: String,
    val email: String,
    val birthday: String
)