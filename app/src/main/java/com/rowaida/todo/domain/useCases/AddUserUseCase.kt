package com.rowaida.todo.domain.useCases

import com.rowaida.todo.data.repositories.UserRepository
import com.rowaida.todo.data.models.User

class AddUserUseCase (private val userRepository: UserRepository) {

    operator fun invoke(user: User) = userRepository.addUser(user)

}