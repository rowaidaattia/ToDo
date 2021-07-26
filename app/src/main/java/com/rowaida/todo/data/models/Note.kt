package com.rowaida.todo.data.models

import java.util.Date

data class Note (
    val id: Int = 0,
    val username: String,
    val name: String,
    val description: String,
    val status: Status,
    val owner: String,
    val date: Date?
)