package com.rowaida.todo.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.data.models.Gender
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.viewModel.UserViewModel
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoToast
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NAME_SHADOWING")
class SignupActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var signupButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        viewModel = ViewModelProvider(this, ToDoViewModelFactory)
                .get(UserViewModel::class.java)

        initializeButton()

        initializeDatePicker()

    }

    private fun initializeButton() {
        signupButton = findViewById(R.id.signup_button2)

        signupButton.setOnClickListener {
            signupClicked()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initializeDatePicker() {
        val birthdayText: TextInputEditText? = findViewById(R.id.birthday)
        val outputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        birthdayText?.inputType = InputType.TYPE_NULL
        birthdayText?.setTextColor(R.color.black)
        birthdayText?.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                birthdayText.setText(outputDateFormat.format(it))
            }
            datePicker.show(supportFragmentManager, "tag")
        }
    }

    private fun check(text: String, error: TextInputLayout) : Boolean {
        return if (TextUtils.isEmpty(text)) {
            error.error = getString(R.string.missing_fields)
            false
        } else {
            error.error = null
            true
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun signupClicked() {

        val username = findViewById<EditText>(R.id.username_signup).text.toString()
        val password = findViewById<EditText>(R.id.password_signup).text.toString()
        val email = findViewById<EditText>(R.id.email_signup).text.toString()
        val birthday = findViewById<EditText>(R.id.birthday).text.toString()
        val male = findViewById<RadioButton>(R.id.male).isChecked
        val female = findViewById<RadioButton>(R.id.female).isChecked

        val checkUser = check(username, findViewById(R.id.usernameError2))
        val checkEmail = check(email, findViewById(R.id.emailError2))
        val checkPass = check(password, findViewById(R.id.passError2))
        val checkBirth = check(birthday, findViewById(R.id.birthError2))

        if (checkUser && checkEmail && checkPass && checkBirth && (male || female)) {
            //add user
            val gender = if (male) Gender.MALE else Gender.FEMALE
            val date = SimpleDateFormat("dd/MM/yyyy").parse(birthday)
            val user = User(username, password, gender, email, date, username, AccountType.ADMIN)

            if (viewModel.addUser(user).toInt() != -1) {
                ToDoNavigation.goToNotes(username, AccountType.ADMIN.toString(), this, NotesAdminActivity::class.java)
            }
            else {
                ToDoToast.toast(applicationContext, applicationContext.getString(R.string.exist_credentials))
            }

        }

    }

}