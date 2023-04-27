package com.example.fooddelieveryapp.DaoTests

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.Dao.Cart
import com.example.fooddelieveryapp.Dao.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CartDaoTests {
    lateinit var mDataBase: AppDatabase
    @Before
    fun initDB() {
        mDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry. getInstrumentation().context,
                AppDatabase::class.java).build()

    }

    @Test
    fun testInsertAndGetCart() {
        val cart = Cart(1, 1, 1)

        mDataBase.getCartDao().addCart(cart)
        val list = mDataBase.getCartDao().getCartById(1)
        assertEquals(cart, list[0])
    }

    @After
    fun closeDb(){
        mDataBase.close()
    }
}