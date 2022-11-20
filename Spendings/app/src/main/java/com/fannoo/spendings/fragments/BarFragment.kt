package com.fannoo.spendings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.fannoo.spendings.R
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.FragmentBarBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class BarFragment : Fragment() {

    private lateinit var binding: FragmentBarBinding
    private lateinit var items: List<ExpenseItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.menu[2].isChecked = true
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_barFragment_to_mainFragment)
                    true
                }
                R.id.p1 -> {
                    findNavController().navigate(R.id.action_barFragment_to_pieFragment)
                    true
                }
                R.id.p2 -> {
                    true
                }
                else -> false
            }
        }

        binding.spYear.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            yearList
        )
        binding.spYear.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                binding.barText.text = "Money spent in ${yearList[position]}"
                thread {
                    items = SpendingsDatabase.getDatabase(requireContext()).expenseItemDao().getByYear(yearList[position], ExpenseItem.spendingType.EXPENSE)
                    requireActivity().runOnUiThread {
                        loadData(yearList[position])
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                thread {
                    items = SpendingsDatabase.getDatabase(requireContext()).expenseItemDao().getByYear("2022", ExpenseItem.spendingType.EXPENSE)
                    requireActivity().runOnUiThread {
                        loadData("2022")
                    }
                }
            }
        }
    }

    private fun loadData(year: String) {
        val list = getList()

        val barEntries = ArrayList<BarEntry>()
        var i = 0
        repeat(list.size) {
            barEntries.add(BarEntry((i+1).toFloat(), list[i].toFloat()))
            i++
        }


        val barDataSet = BarDataSet(barEntries, year)
        barDataSet.color = resources.getColor(R.color.secondaryLightColor, requireActivity().theme)

        binding.barChart.data = BarData(barDataSet)
        binding.barChart.xAxis.textColor = resources.getColor(R.color.secondaryLightColor, requireActivity().theme)
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        binding.barChart.animateXY(0,2000)
    }

    private fun getList(): List<Int> {
        val list: ArrayList<Int> = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        items.forEach {
            list[it.date.subSequence(5,7).toString().toInt()-1] += it.amount
        }

        return list
    }

    private val months = arrayListOf(
        "january",
        "february",
        "march",
        "april",
        "may",
        "june",
        "july",
        "august",
        "september",
        "october",
        "november",
        "december"
    )

    private val yearList = listOf("2022", "2021", "2020", "2019", "2018")

}