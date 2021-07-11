package com.rowaida.todo.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.viewModel.UserViewModel
import com.rowaida.todo.utils.Navigation
import com.rowaida.todo.utils.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var loginButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(UserViewModel::class.java)

        initializeButton()
    }

    private fun initializeButton() {
        loginButton = findViewById(R.id.login_button2)

        loginButton.setOnClickListener {
            loginCLicked()
        }
    }

    private fun loginCLicked() {

        val usernameOrEmail = findViewById<EditText>(R.id.username_login).text.toString()
        val password = findViewById<EditText>(R.id.password_login).text.toString()

        if (TextUtils.isEmpty(usernameOrEmail) || TextUtils.isEmpty(password)) {
            Toast.toast(applicationContext, applicationContext.getString(R.string.missing_fields))
        }

        else {
            if (viewModel.checkUser(usernameOrEmail, password)) {
                val username = viewModel.getUsername(usernameOrEmail)
                when(viewModel.getAccountType(username)) {
                    AccountType.ADMIN ->
                        Navigation.goToNotes(username, AccountType.ADMIN.toString(), this, NotesAdminActivity::class.java)
                    AccountType.SUB_ACCOUNT ->
                        Navigation.goToNotes(username, AccountType.SUB_ACCOUNT.toString(), this, NotesSubAccountActivity::class.java)
                }
            }
            else {
                Toast.toast(applicationContext, applicationContext.getString(R.string.invalid_credentials))
            }
        }
    }

//    private fun goToNotes(username: String) {
//        SharedPreference(applicationContext).writeString(Constants.login, username)
//        val bundle = Bundle()
//        bundle.putString(Constants.username, username)
//        this.finish()
//        Navigation.goToActivity(bundle, this, NotesActivity::class.java)
//    }

}