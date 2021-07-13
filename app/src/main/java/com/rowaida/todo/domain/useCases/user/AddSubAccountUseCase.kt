package com.rowaida.todo.domain.useCases.user

import com.rowaida.todo.data.repositories.UserRepositoryImp

class AddSubAccountUseCase (private val userRepository: UserRepositoryImp) {

    suspend operator fun invoke(admin: String, subAccount: String) =
        userRepository.addSubAccount(admin, subAccount)

}