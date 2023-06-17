package com.example.fooddelieveryapp.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CartItem::class],version =1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getCartItemDao():CartItemDao
    companion object {

        private var INSTANCE: AppDatabase? =null

        fun getInstance(context: Context?): AppDatabase?{
            if (INSTANCE == null) {
                // just trust that context isnt null in this part
                INSTANCE =Room
                    .databaseBuilder(context!!,AppDatabase::class.java, "users_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}