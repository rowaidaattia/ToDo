package com.rowaida.todo.presentation.activity

import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.*


class NotesAdminActivity : NotesActivity() {

    override fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(createFragment(getString(R.string.myTasks)), getString(R.string.myTasks))
        adapter.addFragment(createFragment(getString(R.string.subAccountTasks)), getString(R.string.subAccountTasks))
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
            R.id.logout_admin_button -> {
                logout()
                true
            }
            R.id.add_admin_button -> {
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
            R.id.delete_admin_button -> {
                deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun assignNote() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.enterNote))
        alert.setView(edittext)
        alert.setPositiveButton(
            getString(R.string.next)
        ) { _, _ -> //What ever you want to do with the value
            // setup the alert builder
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.chooseUser))

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
            builder.setPositiveButton(getString(R.string.done)) { dialog, which ->
                // user clicked OK
                runBlocking {
                    noteViewModel.addNote(
                        Note(
                            username = subAccount,
                            name = edittext.text.toString(),
                            description = "Description",
                            status = Status.IN_PROGRESS,
                            owner = username,
                            date = Calendar.getInstance().time
                        ))
                    updateFragment(1, noteViewModel.getSubAccountsNotes(username))
                }
                edittext.setText("")
            }
            builder.setNegativeButton(getString(R.string.cancel), null)

            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()
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

    private fun addAccount() {
        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.chooseUser))

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
        builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
            // user clicked OK
            userViewModel.addSubAccount(username, subAccount)
        }
        builder.setNegativeButton(getString(R.string.cancel), null)

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()

    }

}

