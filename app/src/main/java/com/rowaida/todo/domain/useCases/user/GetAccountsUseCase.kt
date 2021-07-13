package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepositoryImp

class GetAccountsUseCase (private val userRepository: UserRepositoryImp) {

    suspend operator fun invoke(username: String) : List<String> =
        userRepository.getAccounts(username)

}