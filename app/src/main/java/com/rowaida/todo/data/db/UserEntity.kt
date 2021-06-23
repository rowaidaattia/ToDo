package com.rowaida.todo.data.db

import androidx.room.*

@Entity(tableName = "User", indices = [Index(value = ["username", "email"], unique = true)])
data class UserEntity(
    @PrimaryKey val username: String,
    val password: String?,
    val gender: String?,
    val email: String?,
    val birthday: String?
)