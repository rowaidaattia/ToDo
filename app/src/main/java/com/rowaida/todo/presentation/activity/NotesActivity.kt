package com.rowaida.todo.presentation.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
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
import java.util.*


open class NotesActivity : AppCompatActivity(), NotesInterface {

    lateinit var username : String
    lateinit var edittext: EditText
    lateinit var noteViewModel: NoteViewModel
    lateinit var userViewModel: UserViewModel
    private lateinit var tabViewpager: ViewPager
    private lateinit var tabTabLayout: TabLayout
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
        tabTabLayout = findViewById(R.id.tab_tablayout)
        tabToolbar = findViewById(R.id.toolbar)

        setSupportActionBar(tabToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupViewPager(tabViewpager)

        // If we don't use setupWithViewPager() method then
        // tabs are not used or shown when activity opened
        tabTabLayout.setupWithViewPager(tabViewpager)
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

    private fun onUpdateClicked(updateNote: Note) {
        runBlocking {
            noteViewModel.updateNote(
                Note(
                    id = updateNote.id,
                    username = updateNote.username,
                    name = updateNote.name,
                    description = edittext.text.toString(),
                    status = updateNote.status,
                    owner = updateNote.username,
                    date = updateNote.date
                )
            )
            updateFragment(0, noteViewModel.getNotes(username))
        }
    }

    override fun onEditNoteClicked(updateNote: Note) {
        val alert = AlertDialog.Builder(this)
        val edittext = EditText(applicationContext)
        edittext.setText(updateNote.description)
        alert.setMessage(getString(R.string.editNote) + updateNote.name)
        alert.setView(edittext)
        alert.setPositiveButton(
            getString(R.string.update)
        ) { _, _ -> //What ever you want to do with the value
            onUpdateClicked(updateNote)
        }

        alert.setNegativeButton(
            getString(R.string.cancel)
        ) { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    fun onLogoutClicked() {
        ToDoSharedPreference(this).remove(ToDoConstants.login)
        this.finish()
        ToDoNavigation.goToActivity(context = this, activityClass = LoginActivity::class.java)
    }

    private fun onSaveClicked(name: String, description: String) {
        runBlocking {
            noteViewModel.addNote(
                Note(
                    username = username,
                    name = name,
                    description = description,
                    status = Status.IN_PROGRESS,
                    owner = username,
                    date = Calendar.getInstance().time
                ))
            updateFragment(0, noteViewModel.getNotes(username))
        }
    }

    fun onAddMyNoteClicked() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_add_task)
        val save = bottomSheetDialog.findViewById<Button>(R.id.save_button)
        val cancel = bottomSheetDialog.findViewById<Button>(R.id.cancel_button)
        val name = bottomSheetDialog.findViewById<TextInputEditText>(R.id.task_name)
        val description = bottomSheetDialog.findViewById<TextInputEditText>(R.id.task_description)

        save?.setOnClickListener {
            onSaveClicked(name?.text.toString(), description?.text.toString())
            bottomSheetDialog.dismiss()
        }

        cancel?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    fun onDeleteAllNotesClicked() {
        GlobalScope.launch {
            noteViewModel.deleteAllNotes(username)
        }
    }
}

