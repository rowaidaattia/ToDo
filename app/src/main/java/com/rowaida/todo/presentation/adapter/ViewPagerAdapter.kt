package com.rowaida.todo.presentation.adapter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

// This "ViewPagerAdapter" class overrides functions which are
// necessary to get information about which item is selected
// by user, what is title for selected item and so on.*/
//FIXME Fragment pager adapter is deprecated
class ViewPagerAdapter(supportFragmentManager: FragmentManager, lifeCycle: Lifecycle) :
    FragmentStateAdapter(supportFragmentManager, lifeCycle) {

    // objects of arraylist. One is of Fragment type and
    // another one is of String type.*/
    //FIXME why there is 1 in vars name
    private var fragmentList: ArrayList<Fragment> = ArrayList()
    private var fragmentTitleList: ArrayList<String> = ArrayList()

    // this function adds the fragment and title in 2 separate arraylist.
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}