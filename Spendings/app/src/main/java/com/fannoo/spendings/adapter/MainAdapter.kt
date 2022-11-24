package com.fannoo.spendings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fannoo.spendings.R
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.ItemSpendingListBinding
import kotlin.concurrent.thread

class MainAdapter(val context: Context) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val items = mutableListOf<ExpenseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemSpendingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val spendingItem = items[position]
        holder.binding.name.text = getNameString(spendingItem)
        holder.binding.category.text = spendingItem.category
        holder.binding.amount.text = buildString {
            append(spendingItem.amount)
            append(" Ft")
        }
        holder.binding.date.text = spendingItem.date
        holder.binding.listIcon.setImageResource(getPicture(spendingItem))
        holder.binding.deleteButton.setOnClickListener {
            deleteItem(spendingItem, position)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class MainViewHolder(val binding: ItemSpendingListBinding) :
        RecyclerView.ViewHolder(binding.root)


    private fun deleteItem(item: ExpenseItem, pos: Int) {
        items.remove(item)
        thread {
            SpendingsDatabase.getDatabase(context).expenseItemDao().deleteItem(item)
        }
        notifyItemRemoved(pos)
    }

    fun update(item: List<ExpenseItem>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    private fun getPicture(item: ExpenseItem): Int {
        return when (item.type) {
            ExpenseItem.spendingType.EXPENSE -> R.drawable.ic_green_payment_48
            ExpenseItem.spendingType.INCOME -> R.drawable.ic_green_income_48
        }
    }

    private fun getNameString(item : ExpenseItem) : String {
        return when(item.type) {
            ExpenseItem.spendingType.EXPENSE -> "Name: ${item.name}"
            ExpenseItem.spendingType.INCOME -> "From: ${item.name}"
        }
    }


}