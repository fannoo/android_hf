package com.fannoo.spendings.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExpenseItem::class], version = 1)
abstract class SpendingsDatabase : RoomDatabase() {
    abstract fun expenseItemDao() : ExpenseItemDao

    companion object {
        @Volatile
        private var INSTANCE : SpendingsDatabase? = null

        fun getDatabase(applicationContext: Context) : SpendingsDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    applicationContext,
                    SpendingsDatabase::class.java,
                    "spendings"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}