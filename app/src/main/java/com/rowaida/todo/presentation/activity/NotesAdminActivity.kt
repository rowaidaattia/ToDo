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
import com.rowaida.todo.presentation.viewModel.UserViewModel
import com.rowaida.todo.utils.Constants
import com.rowaida.todo.utils.Navigation
import com.rowaida.todo.utils.SharedPreference
import kotlinx.coroutines.runBlocking


class NotesAdminActivity : AppCompatActivity() {

    lateinit var username : String
    private lateinit var edittext: EditText
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var userViewModel: UserViewModel
//    private lateinit var notes : List<Note>
    private lateinit var tabViewpager: ViewPager
    private lateinit var tabTablayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        username = intent.getStringExtra(Constants.username).toString()
        edittext = EditText(applicationContext)

        noteViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(NoteViewModel::class.java)

        userViewModel = ViewModelProvider(this, ToDoViewModelFactory)
            .get(UserViewModel::class.java)

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

        val subAccountsFragment = NotesFragment()
        val subAccountsBundle = Bundle()
        subAccountsBundle.putString("TabName", "Sub Accounts")
        subAccountsBundle.putString(Constants.username, username)
        subAccountsFragment.arguments = subAccountsBundle

        adapter.addFragment(subAccountsFragment, "Sub Accounts Tasks")

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
            R.id.assign_button -> {
                assignNote()
                true
            }
            R.id.add_account_button -> {
                addAccount()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun assignNote() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage("Enter Your Note")
        alert.setView(edittext)
        alert.setPositiveButton(
            "Next"
        ) { _, _ -> //What ever you want to do with the value
            // setup the alert builder
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose User")

            // add a radio button list
            val users = runBlocking {
                userViewModel.getSubAccounts(username).toTypedArray()
            }
            var subAccount = users[0]
            val checkedItem = 0
            builder.setSingleChoiceItems(users, checkedItem) { dialog, which ->
                // user checked an item
                subAccount = users[which]
            }

            // add OK and Cancel buttons
            builder.setPositiveButton("DONE") { dialog, which ->
                // user clicked OK
                runBlocking {
                    noteViewModel.addNote(
                        Note(
                            username = subAccount,
                            note = edittext.text.toString(),
                            status = Status.IN_PROGRESS,
                            owner = username
                        ))
                    updateFragment(1, noteViewModel.getSubAccountsNotes(username))
                }
                edittext.setText("")
            }
            builder.setNegativeButton("Cancel", null)

            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
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

    private fun addAccount() {
        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose User")

        // add a radio button list
        val users = runBlocking {
            userViewModel.getAccounts(username).toTypedArray()
        }
        var subAccount = users[0]
        val checkedItem = 0
        builder.setSingleChoiceItems(users, checkedItem) { dialog, which ->
            // user checked an item
            subAccount = users[which]
        }


        // add OK and Cancel buttons
        builder.setPositiveButton("OK") { dialog, which ->
            // user clicked OK
            userViewModel.addSubAccount(username, subAccount)
        }
        builder.setNegativeButton("Cancel", null)

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()

    }

    fun updateFragment(item: Int?, notes: List<Note>) {
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
                updateFragment(0, noteViewModel.getNotes(username))
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

