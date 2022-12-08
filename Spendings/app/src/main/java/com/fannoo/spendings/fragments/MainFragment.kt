package com.fannoo.spendings.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fannoo.spendings.R
import com.fannoo.spendings.adapter.MainRecyclerViewAdapter
import com.fannoo.spendings.data.SpendingsDatabase
import com.fannoo.spendings.databinding.FragmentMainBinding
import kotlin.concurrent.thread


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var database: SpendingsDatabase
    private lateinit var adapter: MainRecyclerViewAdapter

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

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    true
                }
                R.id.p1 -> {
                    findNavController().navigate(R.id.action_mainFragment_to_pieFragment)
                    true
                }
                R.id.p2 -> {
                    findNavController().navigate(R.id.action_mainFragment_to_barFragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun initRecyclerView() {
        adapter = MainRecyclerViewAdapter(requireContext())
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