package com.fannoo.spendings.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.fragments.AddExpenseFragment
import com.fannoo.spendings.fragments.AddFragment
import com.fannoo.spendings.fragments.AddIncomeFragment

class AddPagerAdapter(addFragment: AddFragment) : FragmentStateAdapter(addFragment) {

    companion object {
        private const val NUM_PAGES: Int = 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0) return AddExpenseFragment(ExpenseItem.spendingType.EXPENSE)
        else if(position == 1) return AddIncomeFragment(ExpenseItem.spendingType.INCOME)
        else return AddExpenseFragment(ExpenseItem.spendingType.EXPENSE)
    }

    override fun getItemCount(): Int = NUM_PAGES

}