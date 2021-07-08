package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepository

class AddSubAccountUseCase (private val userRepository: UserRepository) {

    suspend operator fun invoke(admin: String, subAccount: String) =
        userRepository.addSubAccount(admin, subAccount)

}