package com.rowaida.todo.data.db

import androidx.room.*

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val username: String,
    @ColumnInfo val password: String,
    @ColumnInfo val notes: String?
)