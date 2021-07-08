package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepository

class GetAccountsUseCase (private val userRepository: UserRepository) {

    suspend operator fun invoke(username: String) : List<String> =
        userRepository.getAccounts(username)

}