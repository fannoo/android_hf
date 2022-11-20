package com.fannoo.spendings.data

import androidx.room.*

@Dao
interface ExpenseItemDao {
    @Query("select * from expense order by date asc")
    fun getAll() : List<ExpenseItem>

    @Query("SELECT * FROM expense WHERE date LIKE :year || '%' AND type = :typ ORDER BY date ASC")
    fun getByYear(year : String, typ : ExpenseItem.spendingType) : List<ExpenseItem>

    @Query("SELECT * FROM expense WHERE date LIKE :month || '.%'")
    fun getByMonth(month : String) : List<ExpenseItem>

    @Insert
    fun insert(expense : ExpenseItem) : Long

    @Update
    fun update(expense : ExpenseItem)

    @Delete
    fun deleteItem(expense : ExpenseItem)
}