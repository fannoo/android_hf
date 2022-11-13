package com.fannoo.spendings.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fannoo.spendings.R
import com.fannoo.spendings.adapter.MainAdapter
import com.fannoo.spendings.data.ExpenseItem
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.FragmentMainBinding
import java.io.Serializable
import kotlin.concurrent.thread


class MainFragment : Fragment() /*,MainAdapter.SpendingItemListener*/, AddFragment.newItemListener {

    private lateinit var binding: FragmentMainBinding

    private lateinit var database: SpendingsDatabase
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = SpendingsDatabase.getDatabase(requireContext())

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    }

    /*override fun onItemChanged(item: ExpenseItem) {
        thread {
            database.expenseItemDao().update(item)
        }
    }*/

    override fun onItemCreated(newItem : ExpenseItem) {
        thread {
            val insertId = database.expenseItemDao().insert(newItem)
            newItem.id = insertId
            requireActivity().runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = MainAdapter()
        binding.mainRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycleView.adapter = adapter
        loadItems()
    }

    private fun loadItems() {
        thread {
            val items = database.expenseItemDao().getAll()
            requireActivity().runOnUiThread {
                adapter.update(items)
            }
        }
    }

}