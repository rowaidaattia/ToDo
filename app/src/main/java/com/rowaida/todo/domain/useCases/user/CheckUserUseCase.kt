package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepositoryImp

class CheckUserUseCase (private val userRepository: UserRepositoryImp) {

    suspend operator fun invoke(usernameOrEmail: String, password: String) : Boolean =
        userRepository.checkUser(usernameOrEmail, password)

}