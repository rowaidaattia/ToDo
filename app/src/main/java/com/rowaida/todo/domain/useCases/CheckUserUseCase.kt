package com.rowaida.todo.domain.useCases

import com.rowaida.todo.data.models.User
import com.rowaida.todo.data.repositories.UserRepository

class CheckUserUseCase (private val userRepository: UserRepository) {

    operator fun invoke(user: User) = userRepository.checkUser(user)

}