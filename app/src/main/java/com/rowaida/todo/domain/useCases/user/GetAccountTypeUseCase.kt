package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.data.repositories.UserRepositoryImp

class GetAccountTypeUseCase (private val userRepository: UserRepositoryImp) {

    suspend operator fun invoke(username: String) : AccountType =
        userRepository.getAccountType(username)

}