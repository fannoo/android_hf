package com.fannoo.spendings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.FragmentAddIncomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class AddIncomeFragment(private val spendType : ExpenseItem.spendingType) : Fragment() {

    private lateinit var binding: FragmentAddIncomeBinding
    private var dateget : Long? = MaterialDatePicker.todayInUtcMilliseconds()
    private val format = SimpleDateFormat("yyyy.MM.dd", Locale.FRANCE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expenseAddButton.setOnClickListener {
            if(isValid()) {
                addNewItem(getExepenseItem())
                requireActivity().onBackPressed()
            }
            else
                Snackbar.make(requireContext(), view, "fill in all fields please", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.expenseAddButton)
                    .show()
        }

        binding.expenseCancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.expenseDate.text = format.format(MaterialDatePicker.todayInUtcMilliseconds())
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        picker.addOnPositiveButtonClickListener {
            dateget = picker.selection
            binding.expenseDate.text = format.format(dateget)
        }

        binding.expenseDate.setOnClickListener {
            picker.show(parentFragmentManager, "tag")
        }

    }

    private fun isValid() : Boolean {
        return (binding.expenseName.editText?.text!!.isNotEmpty() &&
                binding.expenseAmount.editText?.text!!.isNotEmpty())
    }

    private fun getExepenseItem() : ExpenseItem {
        return ExpenseItem(
            type = spendType,
            name = binding.expenseName.editText?.text.toString(),
            amount = binding.expenseAmount.editText?.text.toString().toInt(),
            date = format.format(dateget),
            category = "income"
        )
    }

    private fun addNewItem(newItem : ExpenseItem) {
        thread {
            val insertID = SpendingsDatabase.getDatabase(requireContext()).expenseItemDao().insert(newItem)
            newItem.id = insertID
        }

    }

}