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

    private lateinit var userViewModel: UserViewModel
    private lateinit var loginButton : Button
    private lateinit var signUpButton : Button
    private lateinit var emailError : TextInputLayout
    private lateinit var passwordError : TextInputLayout
    private lateinit var usernameOrEmail: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(UserViewModel::class.java)

        initializeButtons()

        initializeFields()
    }

    private fun initializeFields() {
        emailError = findViewById(R.id.emailError)
        passwordError = findViewById(R.id.passError)
        usernameOrEmail = findViewById(R.id.username_login)
        password = findViewById(R.id.password_login)
    }

    private fun initializeButtons() {
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            onLoginClicked()
        }

        signUpButton = findViewById(R.id.go_to_signUp)

        signUpButton.setOnClickListener {
            onSignUpClicked()
        }

    }

    private fun onSignUpClicked() {
        ToDoNavigation.goToActivity(context = this, activityClass = SignupActivity::class.java)
    }

    private fun checkUsernameOrEmail(usernameOrEmail: String) : Boolean {

        return if (TextUtils.isEmpty(usernameOrEmail)) {
            emailError.error = getString(R.string.missing_usernameOrEmail)
            false
        } else {
            emailError.error = null
            true
        }
    }

    private fun checkPassword(password: String) : Boolean {

        return if (TextUtils.isEmpty(password)) {
            passwordError.error = getString(R.string.missing_password)
            false
        } else {
            passwordError.error = null
            true
        }
    }

    private fun onLoginClicked() {

        val checkUser = checkUsernameOrEmail(usernameOrEmail.text.toString())
        val checkPass = checkPassword(password.text.toString())

        if (checkUser && checkPass) {
            if (userViewModel.checkUser(usernameOrEmail.text.toString(), password.text.toString())) {
                val username = userViewModel.getUsername(usernameOrEmail.text.toString())
                when(userViewModel.getAccountType(username)) {
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