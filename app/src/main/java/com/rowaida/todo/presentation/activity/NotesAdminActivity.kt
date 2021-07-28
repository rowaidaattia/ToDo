package com.rowaida.todo.presentation.activity

import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter
import kotlinx.coroutines.runBlocking
import java.util.*

//FIXME fix warnings
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

    //FIXME all methods that happen after user clicks on button/menu/.... should be named onButtonFunctionClicked() like onLogoutClicked() onAddMyNoteClicked to represent user action
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


        val users = //FIXME move this to another method
             runBlocking {
            userViewModel.getSubAccounts(username).toTypedArray()
        }

        //FIXME move bottom sheet init to another method
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_assign_task)
        val assign = bottomSheetDialog.findViewById<Button>(R.id.assign_button)
        val cancel = bottomSheetDialog.findViewById<Button>(R.id.cancel_assign_button)
        val name = bottomSheetDialog.findViewById<TextInputEditText>(R.id.assign_task_name)
        val description = bottomSheetDialog.findViewById<TextInputEditText>(R.id.assign_task_description)
        val spinner = bottomSheetDialog.findViewById<Spinner>(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter(
            this, android.R.layout.simple_spinner_item, users).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner?.adapter = adapter
        }


        assign?.setOnClickListener {
            //FIXME move this to another method
            runBlocking {
                noteViewModel.addNote(
                    Note(
                        username = spinner?.selectedItem.toString(),
                        name = name?.text.toString(),
                        description = description?.text.toString(),
                        status = Status.IN_PROGRESS,
                        owner = username,
                        date = Calendar.getInstance().time
                    ))
                updateFragment(1, noteViewModel.getSubAccountsNotes(username))
            }
            bottomSheetDialog.dismiss()
        }

        cancel?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun addAccount() {
        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.chooseUser))

        // add a radio button list
        val users =//FIXME move this to another method
            runBlocking {
            userViewModel.getAccounts(username).toTypedArray()
        }
        var subAccount = users[0]
        //FIXME why do you need this val ?
        val checkedItem = 0
        builder.setSingleChoiceItems(users, checkedItem) { dialog, which ->
            // user checked an item
            //FIXME rename which
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

