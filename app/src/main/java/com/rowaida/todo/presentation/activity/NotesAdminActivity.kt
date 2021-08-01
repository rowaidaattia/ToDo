package com.rowaida.todo.presentation.activity

import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.rowaida.todo.R
import com.rowaida.todo.data.models.Note
import com.rowaida.todo.data.models.Status
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter
import kotlinx.coroutines.runBlocking
import java.util.*

class NotesAdminActivity : NotesActivity() {

    override fun setupViewPager(viewpager: ViewPager2) {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(createFragment(getString(R.string.myTasks)), getString(R.string.myTasks))
        adapter.addFragment(createFragment(getString(R.string.subAccountTasks)), getString(R.string.subAccountTasks))
        // setting adapter to view pager.
        viewpager.adapter = adapter
        val tabTitles = arrayOf(getString(R.string.myTasks), getString(R.string.subAccountTasks))
        TabLayoutMediator(tabTabLayout, tabViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notes_admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_admin_button -> {
                onLogoutClicked()
                true
            }
            R.id.add_admin_button -> {
                onAddMyNoteClicked()
                true
            }
            R.id.assign_button -> {
                onAssignNoteClicked()
                true
            }
            R.id.add_account_button -> {
                onAddAccountClicked()
                true
            }
            R.id.delete_admin_button -> {
                onDeleteAllNotesClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getSubAccounts() : Array<String> {
        return runBlocking {
            userViewModel.getSubAccounts(username).toTypedArray()
        }
    }

    private fun initializeAssignBottomSheet(users: Array<String>) {
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
            onAssignClicked(spinner?.selectedItem.toString(), name?.text.toString(), description?.text.toString())

            bottomSheetDialog.dismiss()
        }

        cancel?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private fun onAssignNoteClicked() {

        val users = getSubAccounts()

        initializeAssignBottomSheet(users)

    }

    private fun onAssignClicked(username: String, name: String, description: String) {
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
            updateFragment(1, noteViewModel.getSubAccountsNotes(username))
        }
    }

    private fun getAccounts() : Array<String> {
        return runBlocking {
            userViewModel.getAccounts(username).toTypedArray()
        }
    }

    private fun onAddAccountClicked() {
        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.chooseUser))

        // add a radio button list
        val users = getAccounts()

        var subAccount = users[0]

        builder.setSingleChoiceItems(users, 0) { _, index ->
            // user checked an item
            subAccount = users[index]
        }

        // add OK and Cancel buttons
        builder.setPositiveButton(getString(R.string.ok)) { _, _ ->
            // user clicked OK
            userViewModel.addSubAccount(username, subAccount)
        }
        builder.setNegativeButton(getString(R.string.cancel), null)

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()

    }

}

