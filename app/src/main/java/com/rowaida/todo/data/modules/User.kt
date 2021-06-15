package com.rowaida.todo.data.modules

data class User (
    val username: String,
    val password: String,
    val notes: MutableList<Note> = mutableListOf()
)