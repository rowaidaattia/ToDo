package com.rowaida.todo.presentation

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Gender
import com.rowaida.todo.data.models.User
import com.rowaida.todo.framework.ToDoViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        viewModel = ViewModelProvider(this, ToDoViewModelFactory)
                .get(UserViewModel::class.java)

        var birthdayText: EditText? = findViewById(R.id.birthday)
        birthdayText?.inputType = InputType.TYPE_NULL
        birthdayText?.setOnClickListener(View.OnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
            val month: Int = cldr.get(Calendar.MONTH)
            val year: Int = cldr.get(Calendar.YEAR)

            // date picker dialog
            var picker: DatePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> birthdayText.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker.show()
        })
    }

    fun goToNotes(v: View) {
        val username = findViewById<EditText>(R.id.username_signup).text.toString()
        val password = findViewById<EditText>(R.id.password_signup).text.toString()
        val email = findViewById<EditText>(R.id.email_signup).text.toString()
        val birthday = findViewById<EditText>(R.id.birthday).text.toString()
        val male = findViewById<RadioButton>(R.id.male).isChecked
        val female = findViewById<RadioButton>(R.id.female).isChecked

        if (username.trim().isEmpty() || password.trim().isEmpty() ||
            email.trim().isEmpty() || birthday.trim().isEmpty() ||
            !(male || female)) {
            Toast.makeText(applicationContext,
                "Please fill all fields fields",
                Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(applicationContext,
                "Username: $username, Password: $password",
                Toast.LENGTH_LONG).show()

            //add user
            val gender = if (male) Gender.MALE else Gender.FEMALE
            val date = SimpleDateFormat("dd/MM/yyyy").parse(birthday)
            val user = User(username, password, gender, email, date)
            viewModel.addUser(user)

            //println("Check user: " + )

            //go to notes activity
            val intent = Intent(this, NotesActivity::class.java)
            intent.putExtra("Username", username)
            //startActivity(Intent(this, NotesActivity::class.java))
        }

    }

}