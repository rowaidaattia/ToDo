package com.rowaida.todo.presentation

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rowaida.todo.R
import com.rowaida.todo.framework.ToDoViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(UserViewModel::class.java)
    }

    fun goToNotes(v: View) {
        val usernameOrEmail = findViewById<EditText>(R.id.username_login).text.toString()
        val password = findViewById<EditText>(R.id.password_login).text.toString()

        if (usernameOrEmail.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(applicationContext,
                "Please fill username and password fields",
                Toast.LENGTH_LONG).show()
        }
        else {
            //check valid credentials
            if (viewModel.checkUser(usernameOrEmail, password)) {
                val intent = Intent(this, NotesActivity::class.java)
                intent.putExtra("Username", usernameOrEmail)
                startActivity(intent)
            }
            else {
                Toast.makeText(applicationContext,
                    "Incorrect login credentials",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}