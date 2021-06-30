package com.rowaida.todo.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.rowaida.todo.R
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
            Toast.toast(applicationContext, R.string.missing_fields.toString())
        }

        else {
            if (viewModel.checkUser(usernameOrEmail, password)) {
                val username = viewModel.getUsername(usernameOrEmail)
                Navigation.goToNotes(username, this)
            }
            else {
                Toast.toast(applicationContext, R.string.invalid_credentials.toString())
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