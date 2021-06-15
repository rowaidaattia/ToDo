package com.rowaida.todo.data.models

data class User (
    val username: String,
    val password: String,
    val notes: MutableList<Note> = mutableListOf()
)