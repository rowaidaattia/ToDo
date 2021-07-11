package com.rowaida.todo.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter
import com.rowaida.todo.utils.Constants
import kotlinx.coroutines.runBlocking


class NotesAdminActivity : NotesActivity() {

    override fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(createFragment(Constants.myTasks), Constants.myTasks)
        adapter.addFragment(createFragment(Constants.subAccountTasks), Constants.subAccountTasks)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun assignNote() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(Constants.enterNote)
        alert.setView(edittext)
        alert.setPositiveButton(
            Constants.next
        ) { _, _ -> //What ever you want to do with the value
            // setup the alert builder
            val builder = AlertDialog.Builder(this)
            builder.setTitle(Constants.chooseUser)

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
            builder.setPositiveButton(Constants.done) { dialog, which ->
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
            builder.setNegativeButton(Constants.cancel, null)

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
        builder.setTitle(Constants.chooseUser)

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
        builder.setPositiveButton(Constants.ok) { dialog, which ->
            // user clicked OK
            userViewModel.addSubAccount(username, subAccount)
        }
        builder.setNegativeButton(Constants.cancel, null)

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()

    }

}

