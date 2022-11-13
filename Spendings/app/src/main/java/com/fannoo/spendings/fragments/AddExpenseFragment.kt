package com.fannoo.spendings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.FragmentAddExpenseBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import kotlin.concurrent.thread

class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding
    private lateinit var listener : newExpenseListener
    private var dateget : Long? = MaterialDatePicker.todayInUtcMilliseconds()
    private val format = SimpleDateFormat("yy.MM.dd")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        listener = requireParentFragment() as newExpenseListener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.expenseAddButton.setOnClickListener {
            if(isValid())
                addNewItem(getExepenseItem())
            else
                Snackbar.make(requireContext(), view, "tolts ki minden adatot kerlek", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.expenseAddButton)
                    .show()
        }

        binding.expenseCancelButton.setOnClickListener {

        }

        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        picker.addOnPositiveButtonClickListener {
            dateget = picker.selection
            binding.dateText.text = format.format(dateget)
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
            type = ExpenseItem.spendingType.EXPENSE,
            name = binding.expenseName.editText?.text.toString(),
            amount = binding.expenseAmount.editText?.text.toString().toInt(),
            date = format.format(dateget),
            category = "elelmiszer"
        )
    }

    private fun addNewItem(newItem : ExpenseItem) {
        thread {
            val insertID = SpendingsDatabase.getDatabase(requireContext()).expenseItemDao().insert(newItem)
            newItem.id = insertID
        }

    }

    interface newExpenseListener {
        fun onExpenseItemCreated(newItem : ExpenseItem)
    }

}