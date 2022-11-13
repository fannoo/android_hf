package com.fannoo.spendings.data

import androidx.room.*

@Dao
interface ExpenseItemDao {
    @Query("select * from expense")
    fun getAll() : List<ExpenseItem>

    @Insert
    fun insert(expense : ExpenseItem) : Long

    @Update
    fun update(expense : ExpenseItem)

    @Delete
    fun deleteItem(expense : ExpenseItem)

}