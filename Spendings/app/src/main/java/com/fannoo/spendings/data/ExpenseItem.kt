package com.fannoo.spendings.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense")
data class ExpenseItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Long? = null,
    @ColumnInfo(name = "type") var type : spendingType,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "amount") var amount : Int,
    @ColumnInfo(name = "date") var date : String,
    @ColumnInfo(name = "categoryID") var category : String?
    ) {
    enum class spendingType {
        EXPENSE, INCOME;
    }
}

