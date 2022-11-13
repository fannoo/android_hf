package com.fannoo.spendings.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.fannoo.spendings.R
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayoutMediator


class AddFragment : Fragment(), AddExpenseFragment.newExpenseListener {

    private lateinit var binding: FragmentAddBinding
    private lateinit var listener : newItemListener

    override fun onExpenseItemCreated(newItem: ExpenseItem) {
        listener.onItemCreated(newItem)
    }

    interface newItemListener {
        fun onItemCreated(item : ExpenseItem)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume(){
        super.onResume()
        val addPagerAdapter = AddPagerAdapter(this)
        binding.mainViewPager.adapter = addPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.expense)
                1 -> getString(R.string.income)
                else -> ""
            }
        }.attach()
    }


}