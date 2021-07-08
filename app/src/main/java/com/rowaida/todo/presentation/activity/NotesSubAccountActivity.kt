package com.rowaida.todo.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
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
import com.rowaida.todo.utils.Constants
import com.rowaida.todo.utils.Navigation
import com.rowaida.todo.utils.SharedPreference
import kotlinx.coroutines.runBlocking


class NotesSubAccountActivity : AppCompatActivity() {

    private lateinit var username : String
    private lateinit var edittext: EditText
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var notes : List<Note>
    private lateinit var tabViewpager: ViewPager
    private lateinit var tabTablayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        username = intent.getStringExtra(Constants.username).toString()
        edittext = EditText(applicationContext)

        noteViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        // Create the object of Toolbar, ViewPager and
        // TabLayout and use “findViewById()” method*/
        tabViewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        tabTablayout = findViewById<TabLayout>(R.id.tab_tablayout)

        setupViewPager(tabViewpager)

        // If we don't use setupWithViewPager() method then
        // tabs are not used or shown when activity opened
        tabTablayout.setupWithViewPager(tabViewpager)
    }

    // This function is used to add items in arraylist and assign
    // the adapter to view pager
    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        val myTasksFragment = NotesFragment()
        val myTasksBundle = Bundle()
        myTasksBundle.putString("TabName", "My Tasks")
        myTasksBundle.putString(Constants.username, username)
        myTasksFragment.arguments = myTasksBundle

        adapter.addFragment(myTasksFragment, "My Tasks")

        val assignedTasksFragment = NotesFragment()
        val assignedTasksBundle = Bundle()
        assignedTasksBundle.putString("TabName", "Assigned Tasks")
        assignedTasksBundle.putString(Constants.username, username)
        assignedTasksFragment.arguments = assignedTasksBundle

        adapter.addFragment(assignedTasksFragment, "Assigned Tasks")

        // setting adapter to view pager.
        viewpager.adapter = adapter
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
            R.id.add_button -> {
                addMyNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateFragment(item: Int) {
        val fragment =
            supportFragmentManager.findFragmentByTag(
                "android:switcher:" + R.id.tab_viewpager.toString() + ":" + item
            )
        // based on the current position you can then cast the page to the correct Fragment class and call some method inside that fragment to reload the data:
        if (null != fragment) {
            (fragment as NotesFragment).updateList(notes)
        }
    }

    private fun addMyNote() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage("Enter Your Note")
        alert.setView(edittext)
        alert.setPositiveButton(
            Constants.save
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
                notes = noteViewModel.getNotes(username)
            }

            updateFragment(0)

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

