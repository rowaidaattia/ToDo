package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.data.repositories.UserRepository

class GetAccountTypeUseCase (private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String) : AccountType =
        userRepository.getAccountType(username)

}