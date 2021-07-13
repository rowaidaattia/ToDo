package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepositoryImp
import com.rowaida.todo.data.models.User

class AddUserUseCase (private val userRepository: UserRepositoryImp) {

    suspend operator fun invoke(user: User) : Long = userRepository.addUser(user)

}