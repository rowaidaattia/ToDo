package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepositoryImp

class GetUsernameUseCase (private val userRepository: UserRepositoryImp) {

    suspend operator fun invoke(usernameOrEmail: String) : String =
        userRepository.getUsername(usernameOrEmail)

}