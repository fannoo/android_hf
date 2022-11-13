package com.fannoo.spendings.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AddPagerAdapter(addFragment: AddFragment) : FragmentStateAdapter(addFragment) {

    companion object {
        private const val NUM_PAGES: Int = 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0) return AddExpenseFragment()
        else if(position == 1) return AddIncomeFragment()
        else return AddExpenseFragment()
    }

    override fun getItemCount(): Int = NUM_PAGES
}