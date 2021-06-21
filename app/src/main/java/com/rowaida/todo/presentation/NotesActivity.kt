package com.rowaida.todo.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status


class NotesActivity : AppCompatActivity() {

    //val username = intent.getStringExtra("Username")
    val username = "rowaida"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val notes = username?.let { arrayOf(
            Note(123, it, "1st note", Status.DONE),
            Note(456, it, "2nd note", Status.DONE)
        )}

        val notesAdapter = notes?.let { NotesAdapter(it) }

        val recyclerView: RecyclerView = findViewById(R.id.notes_recycler_view)
        recyclerView.adapter = notesAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notes_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_button -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun expandNote(v: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }

}