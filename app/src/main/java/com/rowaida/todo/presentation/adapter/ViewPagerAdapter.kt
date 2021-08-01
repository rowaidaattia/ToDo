package com.rowaida.todo.presentation.adapter

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

// This "ViewPagerAdapter" class overrides functions which are
// necessary to get information about which item is selected
// by user, what is title for selected item and so on.*/
//FIXME Fragment pager adapter is deprecated
class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // objects of arraylist. One is of Fragment type and
    // another one is of String type.*/
    //FIXME why there is 1 in vars name
    private var fragmentList: ArrayList<Fragment> = ArrayList()
    private var fragmentTitleList: ArrayList<String> = ArrayList()

    // returns which item is selected from arraylist of fragments.
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    // returns which item is selected from arraylist of titles.
    @Nullable
    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }

    // returns the number of items present in arraylist.
    override fun getCount(): Int {
        return fragmentList.size
    }

    // this function adds the fragment and title in 2 separate arraylist.
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}