package com.rowaida.todo.domain.useCases

import com.rowaida.todo.data.repositories.UserRepository

class GetUsernameUseCase (private val userRepository: UserRepository) {

    suspend operator fun invoke(usernameOrEmail: String) : String =
        userRepository.getUsername(usernameOrEmail)

}