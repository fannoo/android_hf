package com.fannoo.spendings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.fannoo.spendings.R
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.FragmentPieBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class PieFragment : Fragment() {

    private lateinit var binding : FragmentPieBinding
    private lateinit var items : List<ExpenseItem>
    private var getToday : Long? = MaterialDatePicker.todayInUtcMilliseconds()
    private val format = SimpleDateFormat("yyyy.MM.dd", Locale.FRANCE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.menu[1].isChecked = true
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    findNavController().navigate(R.id.action_pieFragment_to_mainFragment)
                    true
                }
                R.id.p1 -> {
                    true
                }
                R.id.p2 -> {
                    findNavController().navigate(R.id.action_pieFragment_to_barFragment)
                    true
                }
                else -> false
            }
        }

        thread {
            val adatok =
                SpendingsDatabase.getDatabase(requireContext()).expenseItemDao().getByMonth(format.format(getToday).subSequence(0,7).toString())
            requireActivity().runOnUiThread {
                items = adatok
                loadExpense()
            }
        }
    }


    private fun loadExpense() {
        val entries = listOf(
            PieEntry(getSpent(ExpenseItem.spendingType.EXPENSE), "Spent"),
            PieEntry(getSpent(ExpenseItem.spendingType.INCOME), "Income")
        )
        val dataSet = PieDataSet(entries, "Money")
        dataSet.colors = ColorTemplate.PASTEL_COLORS.toList()

        val data = PieData(dataSet)
        binding.chartSpent.data = data
        binding.chartSpent.invalidate()
    }

    private fun getSpent(type : ExpenseItem.spendingType) : Float {
        var osszesen = 0
        items.forEach {
            if(it.type==type) {
                osszesen += it.amount
            }
        }
        return osszesen.toFloat()
    }
}

