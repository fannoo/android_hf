package com.fannoo.spendings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fannoo.spendings.R
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.databinding.ItemSpendingListBinding

class MainAdapter() : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val items = mutableListOf<ExpenseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemSpendingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val spendingItem = items[position]

        holder.binding.name.text = spendingItem.name
        holder.binding.amount.text = spendingItem.amount.toString()
        holder.binding.date.text = spendingItem.date
        holder.binding.category.text = spendingItem.category
        holder.binding.listIcon.setImageResource(getPicture(spendingItem.type))
    }

    override fun getItemCount(): Int = items.size

    inner class MainViewHolder(val binding: ItemSpendingListBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: ExpenseItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(item: List<ExpenseItem>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    private fun getPicture(type : ExpenseItem.spendingType) : Int {
        return when(type) {
            ExpenseItem.spendingType.EXPENSE -> R.drawable.ic_payment_48
            ExpenseItem.spendingType.INCOME -> R.drawable.ic_income_48
        }
    }

}