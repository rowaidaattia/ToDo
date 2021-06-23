package com.rowaida.todo.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.framework.ToDoViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class NotesActivity : AppCompatActivity() {

    lateinit var username : String
    private lateinit var viewModel: NoteViewModel
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var notes : List<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        username = intent.getStringExtra("Username").toString()

        viewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        runBlocking {
            notes = viewModel.getNotes(username)
            notesAdapter = NotesAdapter(notes as MutableList<Note>, viewModel)
        }

        val recyclerView: RecyclerView = findViewById(R.id.notes_recycler_view)
        val recycle: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = recycle

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

    fun addNote(v: View) {
        val alert = AlertDialog.Builder(this)
        val edittext = EditText(this.applicationContext)
        alert.setMessage("Enter Your Note")
        alert.setTitle("Add Note")
        alert.setView(edittext)

        alert.setPositiveButton(
            "Save"
        ) { dialog, whichButton -> //What ever you want to do with the value

//            val note = edittext.text.toString()
            //add note to database
            runBlocking {
                viewModel.addNote(Note(
                    username = username,
                    note = edittext.text.toString(),
                    status = Status.IN_PROGRESS
                ))
                notes = viewModel.getNotes(username)
                notesAdapter.update(notes)
            }

        }

        alert.setNegativeButton(
            "Cancel"
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    fun deleteNote(note : Note) {
        runBlocking {
            viewModel.removeNote(Note(
                id = note.id,
                username = note.username,
                note = note.note,
                status = note.status
            ))
            notes = viewModel.getNotes(username)
            notesAdapter.update(notes)
        }
    }

    fun expandNote(v: View) {
    }

}