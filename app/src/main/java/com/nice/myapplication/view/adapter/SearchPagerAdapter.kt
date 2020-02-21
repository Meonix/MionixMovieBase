package com.nice.myapplication.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nice.myapplication.view.fragment.SearchMovieFragment
import com.nice.myapplication.view.fragment.SearchPeopleFragment
import com.nice.myapplication.view.fragment.SearchTVShowFragment

class SearchPagerAdapter(fm : FragmentManager) :FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> SearchMovieFragment()
            1 -> SearchTVShowFragment()
            else -> {
                return SearchPeopleFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Movies"
            1 -> "TV Shows"
            else -> {
                return "People"
            }
        }
    }
}