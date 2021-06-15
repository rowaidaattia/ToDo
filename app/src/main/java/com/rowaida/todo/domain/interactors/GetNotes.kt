package com.rowaida.todo.domain.interactors

import com.rowaida.todo.data.UserRepository
import com.rowaida.todo.data.modules.User

class GetNotes (private val userRepository: UserRepository) {

    operator fun invoke(user: User) = userRepository.getNotes(user)

}