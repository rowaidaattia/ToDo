package com.rowaida.todo.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.viewModel.UserViewModel
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoToast

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var loginButton : Button
    private lateinit var signupButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(UserViewModel::class.java)

        initializeButtons()
    }

    private fun initializeButtons() {
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            loginCLicked()
        }

        signupButton = findViewById(R.id.signup_button)

        signupButton.setOnClickListener {
            signupClicked()
        }

    }

    private fun signupClicked() {
        ToDoNavigation.goToActivity(null, this, SignupActivity::class.java)
    }

    private fun checkUsernameOrEmail(usernameOrEmail: String) : Boolean {
        val emailError = findViewById<TextInputLayout>(R.id.emailError)

        return if (TextUtils.isEmpty(usernameOrEmail)) {
            emailError.error = getString(R.string.missing_usernameOrEmail)
            false
        } else {
            emailError.error = null
            true
        }
    }

    private fun checkPassword(password: String) : Boolean {
        val passError = findViewById<TextInputLayout>(R.id.passError)

        return if (TextUtils.isEmpty(password)) {
            passError.error = getString(R.string.missing_password)
            false
        } else {
            passError.error = null
            true
        }
    }

    private fun loginCLicked() {

        val usernameOrEmail = findViewById<EditText>(R.id.username_login).text.toString()
        val password = findViewById<EditText>(R.id.password_login).text.toString()

        val checkUser = checkUsernameOrEmail(usernameOrEmail)
        val checkPass = checkPassword(password)

        if (checkUser && checkPass) {
            if (viewModel.checkUser(usernameOrEmail, password)) {
                val username = viewModel.getUsername(usernameOrEmail)
                when(viewModel.getAccountType(username)) {
                    AccountType.ADMIN ->
                        ToDoNavigation.goToNotes(username, AccountType.ADMIN.toString(), this, NotesAdminActivity::class.java)
                    AccountType.SUB_ACCOUNT ->
                        ToDoNavigation.goToNotes(username, AccountType.SUB_ACCOUNT.toString(), this, NotesSubAccountActivity::class.java)
                }
            }
            else {
                ToDoToast.toast(applicationContext, applicationContext.getString(R.string.invalid_credentials))
            }
        }
    }

}