package com.rowaida.todo.presentation.adapter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

// This "ViewPagerAdapter" class overrides functions which are
// necessary to get information about which item is selected
// by user, what is title for selected item and so on.*/
class ViewPagerAdapter// this is a secondary constructor of ViewPagerAdapter class.
    (supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {

    // objects of arraylist. One is of Fragment type and
    // another one is of String type.*/
    private var fragmentList1: ArrayList<Fragment> = ArrayList()
    private var fragmentTitleList1: ArrayList<String> = ArrayList()

    // returns which item is selected from arraylist of fragments.
    override fun getItem(position: Int): Fragment {
        return fragmentList1[position]
    }

    // returns which item is selected from arraylist of titles.
    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList1[position]
    }

    // returns the number of items present in arraylist.
    override fun getCount(): Int {
        return fragmentList1.size
    }

    // this function adds the fragment and title in 2 separate arraylist.
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList1.add(fragment)
        fragmentTitleList1.add(title)
    }
}