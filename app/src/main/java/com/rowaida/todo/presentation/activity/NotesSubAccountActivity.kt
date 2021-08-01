package com.rowaida.todo.presentation.activity

import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.rowaida.todo.R
import com.rowaida.todo.presentation.adapter.ViewPagerAdapter

class NotesSubAccountActivity : NotesActivity() {

    override fun setupViewPager(viewpager: ViewPager2) {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(createFragment(getString(R.string.myTasks)), getString(R.string.myTasks))
        adapter.addFragment(createFragment(getString(R.string.assignedTasks)), getString(R.string.assignedTasks))
        // setting adapter to view pager.
        viewpager.adapter = adapter
        val tabTitles = arrayOf(getString(R.string.myTasks), getString(R.string.assignedTasks))
        TabLayoutMediator(tabTabLayout, tabViewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
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

