package com.rowaida.todo.presentation.activity

import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.rowaida.todo.R
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter

class NotesSubAccountActivity : NotesActivity() {

    override fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(createFragment(getString(R.string.myTasks)), getString(R.string.myTasks))
        adapter.addFragment(createFragment(getString(R.string.assignedTasks)), getString(R.string.assignedTasks))
        // setting adapter to view pager.
        viewpager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.notes_subaccount_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_subaccount_button -> {
                onLogoutClicked()
                true
            }
            R.id.add_subaccount_button -> {
                onAddMyNoteClicked()
                true
            }
            R.id.delete_subaccount_button -> {
                onDeleteAllNotesClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

