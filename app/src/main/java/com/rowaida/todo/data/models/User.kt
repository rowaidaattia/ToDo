package com.rowaida.todo.data.models

import android.provider.ContactsContract
import java.util.*

data class User (
    val username: String,
    val password: String,
    val gender: Gender,
    val email: String,
    val birthday: Date,
    val admin: String,
    val accountType: AccountType
)