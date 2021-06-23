package com.rowaida.todo.domain.useCases

import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.repositories.UserRepository

class CheckUserUseCase (private val userRepository: UserRepository) {

    suspend operator fun invoke(usernameOrEmail: String, password: String) : Boolean =
        userRepository.checkUser(usernameOrEmail, password)

}