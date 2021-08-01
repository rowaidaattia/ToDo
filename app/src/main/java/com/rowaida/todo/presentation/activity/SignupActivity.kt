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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rowaida.todo.R
import com.rowaida.todo.data.models.AccountType
import com.rowaida.todo.data.models.Gender
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.viewModel.UserViewModel
import com.rowaida.todo.utils.ToDoDatePicker
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoToast
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NAME_SHADOWING")
class SignupActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var signUpButton : Button
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var email : EditText
    private lateinit var birthday : EditText
    private lateinit var male : RadioButton
    private lateinit var female : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userViewModel = ViewModelProvider(this, ToDoViewModelFactory)
                .get(UserViewModel::class.java)

        initializeButton()

        initializeDatePicker()

        initializeFields()

    }

    private fun initializeFields() {
        username = findViewById(R.id.username_signUp)
        password = findViewById(R.id.password_signUp)
        email = findViewById(R.id.email_signUp)
        birthday = findViewById(R.id.birthday)
        male = findViewById(R.id.male)
        female = findViewById(R.id.female)
    }

    private fun initializeButton() {
        signUpButton = findViewById(R.id.signUp_button)

        signUpButton.setOnClickListener {
            signUpClicked()
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
            val datePicker = ToDoDatePicker.setDatePicker(applicationContext)

            datePicker.addOnPositiveButtonClickListener {
                birthdayText.setText(outputDateFormat.format(it))
            }
            datePicker.show(supportFragmentManager, "tag")
        }
    }

     private fun checkEmptyField(text: String, error: TextInputLayout) : Boolean {
        return if (TextUtils.isEmpty(text)) {
            error.error = getString(R.string.missing_fields)
            false
        } else {
            error.error = null
            true
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun signUpClicked() {

        val checkUser = checkEmptyField(username.text.toString(), findViewById(R.id.usernameError_signUp))
        val checkEmail = checkEmptyField(email.text.toString(), findViewById(R.id.emailError_signUp))
        val checkPass = checkEmptyField(password.text.toString(), findViewById(R.id.passError_signUp))
        val checkBirth = checkEmptyField(birthday.text.toString(), findViewById(R.id.birthError_signUp))

        if (checkUser && checkEmail && checkPass && checkBirth && (male.isChecked || female.isChecked)) {
            //add user
            val gender = if (male.isChecked) Gender.MALE else Gender.FEMALE
            val date = SimpleDateFormat("dd/MM/yyyy").parse(birthday.text.toString())
            val user = User(username.text.toString(), password.text.toString(), gender,
                email.text.toString(), date, username.text.toString(), AccountType.ADMIN)

            if (userViewModel.addUser(user).toInt() != -1) {
                ToDoNavigation.goToNotes(username.text.toString(), AccountType.ADMIN.toString(),
                    this, NotesAdminActivity::class.java)
            }
            else {
                ToDoToast.toast(applicationContext, applicationContext.getString(R.string.exist_credentials))
            }

        }

    }

}