package com.rowaida.todo.presentation.activity

import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.rowaida.todo.R
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter
import com.rowaida.todo.utils.Constants

class NotesSubAccountActivity : NotesActivity() {

    override fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(createFragment(Constants.myTasks), Constants.myTasks)
        adapter.addFragment(createFragment(Constants.assignedTasks), Constants.assignedTasks)
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
                logout()
                true
            }
            R.id.add_subaccount_button -> {
                addMyNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

