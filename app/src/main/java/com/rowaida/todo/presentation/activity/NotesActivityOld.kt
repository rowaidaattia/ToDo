package com.rowaida.todo.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import com.rowaida.todo.presentation.adapter.NotesAdapter
import com.rowaida.todo.utils.Constants
import com.rowaida.todo.utils.Navigation
import com.rowaida.todo.utils.SharedPreference
import kotlinx.coroutines.runBlocking


class NotesActivityOld : AppCompatActivity() {

    lateinit var username : String
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var notes : List<Note>
    private lateinit var edittext: EditText
    private lateinit var addNoteButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_notes_old)

        initializeButton()

        edittext = EditText(this.applicationContext)

        username = intent.getStringExtra(Constants.username).toString()

        noteViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        initializeAdapter()

    }

    private fun initializeButton() {
//        addNoteButton = findViewById(R.id.add_note_button)
//        addNoteButton.setOnClickListener {
//            addNote()
//        }
    }

    private fun initializeAdapter() {
        runBlocking {
            notes = noteViewModel.getNotes(username)
//            notesAdapter = if (notes.isEmpty()) {
//                NotesAdapter(mutableListOf(), noteViewModel, this@NotesActivityOld)
//            } else {
//                NotesAdapter(notes as MutableList<Note>, noteViewModel, this@NotesActivityOld)
//            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.notes_recycler_view2)
        val recycle: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = recycle

        recyclerView.adapter = notesAdapter
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        edittext.setText(savedInstanceState.getString(Constants.notesDraft, ""))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.notesDraft, edittext.text.toString())
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notes_admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_button -> {
                SharedPreference(applicationContext).remove(Constants.login)
                this.finish()
                Navigation.goToActivity(null, this, MainActivity::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addNote() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage("Enter Your Note")
        alert.setView(edittext)
        alert.setPositiveButton(
            Constants.save
        ) { _, _ -> //What ever you want to do with the value
            //add note to database
            runBlocking {
                noteViewModel.addNote(Note(
                    username = username,
                    note = edittext.text.toString(),
                    status = Status.IN_PROGRESS,
                    owner = username
                ))
                notes = noteViewModel.getNotes(username)
                notesAdapter.update(notes)
            }

            edittext.setText("")

        }

        alert.setNegativeButton(
            Constants.cancel
        ) { dialog, _ ->
            edittext.setText("")
            dialog.dismiss()
        }
        if(edittext.parent != null) {
            (edittext.parent as ViewGroup).removeView(edittext)
        }
        alert.show()
    }

}