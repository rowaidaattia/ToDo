package com.rowaida.todo.presentation.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.framework.ToDoViewModelFactory
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter
import com.rowaida.todo.presentation.viewModel.NoteViewModel
import com.rowaida.todo.presentation.viewModel.UserViewModel
import com.rowaida.todo.utils.ToDoConstants
import com.rowaida.todo.utils.ToDoNavigation
import com.rowaida.todo.utils.ToDoSharedPreference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


open class NotesActivity : AppCompatActivity(), NotesInterface {

    lateinit var username : String
    lateinit var edittext: EditText
    lateinit var noteViewModel: NoteViewModel
    lateinit var userViewModel: UserViewModel
    private lateinit var tabViewpager: ViewPager
    private lateinit var tabTablayout: TabLayout
    private lateinit var tabToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        username = intent.getStringExtra(ToDoConstants.username).toString()
        edittext = EditText(applicationContext)

        noteViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        userViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(UserViewModel::class.java)

        // Create the object of Toolbar, ViewPager and
        // TabLayout and use “findViewById()” method*/
        tabViewpager = findViewById(R.id.tab_viewpager)
        tabTablayout = findViewById(R.id.tab_tablayout)
        tabToolbar = findViewById(R.id.toolbar)

        setSupportActionBar(tabToolbar)
        setupViewPager(tabViewpager)

        // If we don't use setupWithViewPager() method then
        // tabs are not used or shown when activity opened
        tabTablayout.setupWithViewPager(tabViewpager)
    }

    fun createFragment(tabName: String) : NotesFragment {
        val fragment = NotesFragment()
        val bundle = Bundle()
        bundle.putString(ToDoConstants.tabName, tabName)
        bundle.putString(ToDoConstants.username, username)
        fragment.arguments = bundle
        return fragment
    }


    // This function is used to add items in arraylist and assign
    // the adapter to view pager
    open fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        // setting adapter to view pager.
        viewpager.adapter = adapter
    }

    override fun updateFragment(item: Int?, notes: List<Note>) {
        var index = item
        if (item == null) {
            index = tabViewpager.currentItem
        }
        val fragment =
            supportFragmentManager.findFragmentByTag(
                "android:switcher:" + R.id.tab_viewpager.toString() + ":" + index
            )
        // based on the current position you can then cast the page to the correct Fragment class and call some method inside that fragment to reload the data:
        if (null != fragment) {
            //println("NOTES HERE: $notes")
            (fragment as NotesFragment).updateList(notes)
            fragment.initializeProgressBar()
        }
    }

    override fun editNote(updateNote: Note) {
        val alert = AlertDialog.Builder(this)
        val edittext = EditText(applicationContext)
        edittext.setText(updateNote.note)
        alert.setMessage(getString(R.string.editNote))
        alert.setView(edittext)
        alert.setPositiveButton(
            getString(R.string.update)
        ) { _, _ -> //What ever you want to do with the value

            runBlocking {
                noteViewModel.updateNote(
                    Note(
                        id = updateNote.id,
                        username = updateNote.username,
                        note = edittext.text.toString(),
                        status = updateNote.status,
                        owner = updateNote.username,
                    )
                )
                updateFragment(0, noteViewModel.getNotes(username))
            }

        }

        alert.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    fun logout() {
        ToDoSharedPreference(applicationContext).remove(ToDoConstants.login)
        this.finish()
        ToDoNavigation.goToActivity(null, this, MainActivity::class.java)
    }

    fun addMyNote() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.enterNote))
        alert.setView(edittext)
        alert.setPositiveButton(
            getString(R.string.save)
        ) { _, _ -> //What ever you want to do with the value
            //add note to database
            runBlocking {
                noteViewModel.addNote(
                    Note(
                    username = username,
                    note = edittext.text.toString(),
                    status = Status.IN_PROGRESS,
                    owner = username
                ))
                updateFragment(0, noteViewModel.getNotes(username))
            }


            edittext.setText("")

        }

        alert.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, _ ->
            edittext.setText("")
            dialog.dismiss()
        }
        if(edittext.parent != null) {
            (edittext.parent as ViewGroup).removeView(edittext)
        }
        alert.show()
    }

    fun deleteAllNotes() {
        GlobalScope.launch {
            noteViewModel.deleteAllNotes(username)
        }
    }
}

