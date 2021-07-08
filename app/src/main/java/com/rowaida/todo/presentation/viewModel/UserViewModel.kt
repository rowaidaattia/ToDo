package com.rowaida.todo.presentation.viewModel

import android.app.Application
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModel
import com.rowaida.todo.domain.useCases.UseCases
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel (application: Application, useCases: UseCases) :
    ToDoViewModel(application, useCases) {

    fun addUser(user: User) : Long {
        var r: Long
        runBlocking {
            r = useCases.addUserUseCase(user)
        }
        return r
    }

    fun checkUser(usernameOrEmail: String, password: String) : Boolean {
        var r: Boolean
        runBlocking {
            r = useCases.checkUserUseCase(usernameOrEmail, password)
        }
        return r

    }

    fun getUsername(usernameOrEmail: String) : String {
        var r: String
        runBlocking {
            r = useCases.getUsernameUseCase(usernameOrEmail)
        }
        return r
    }

    fun addSubAccount(admin: String, subAccount: String) {
        GlobalScope.launch {
            useCases.addSubAccountUseCase(admin, subAccount)
        }
    }

    suspend fun getSubAccounts(username: String) : List<String> {
        var subAccounts = GlobalScope.async {
            useCases.getSubAccountsUseCase(username)
        }
        return subAccounts.await()

    }

    fun getAccountType(username: String) : AccountType {
        var r: AccountType
        runBlocking {
            r = useCases.getAccountTypeUseCase(username)
        }
        return r
    }

    suspend fun getAccounts(username: String) : List<String> {
        var accounts = GlobalScope.async {
            useCases.getAccountsUseCase(username)
        }
        return accounts.await()

    }

}