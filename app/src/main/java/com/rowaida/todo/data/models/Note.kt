package com.rowaida.todo.data.models

data class Note (
    val id: Int = 0,
    val username: String,
    val note: String,
    val status: Status,
    val owner: String
)