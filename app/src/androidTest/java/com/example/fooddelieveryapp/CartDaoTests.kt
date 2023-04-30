package com.example.fooddelieveryapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.Dao.Cart
import com.example.fooddelieveryapp.Dao.CartItem
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
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
    @Test
    fun testGetCarItems(){
        val cart = Cart(1, 1, 1)
        val cartItem = CartItem(1,"je3fer",1,1.0,1,1)
        mDataBase.getCartDao().addCart(cart)
        mDataBase.getCartItemDao().addCartItem(cartItem)

        val list = mDataBase.getCartItemDao().getCartItemsbyCart(1)
        assertEquals(cartItem,list[0])
    }
    @Test
    fun testDeleteCartItem(){
        val cart = Cart(1, 1, 1)
        val cartItem = CartItem(1,"je3fer",1,1.0,1,1)
        mDataBase.getCartDao().addCart(cart)
        mDataBase.getCartItemDao().addCartItem(cartItem)

        mDataBase.getCartItemDao().deleteCartItem(
            mDataBase.getCartItemDao().getCartItemById(1)[0]
        )

        assertTrue(mDataBase.getCartItemDao().getCartItemById(1).isEmpty())
    }

    @Test
    fun testUpdateCartItem(){
        val cart = Cart(1, 1, 1)
        val cartItem = CartItem(1,"je3fer",1,1.0,1,1)
        mDataBase.getCartDao().addCart(cart)
        mDataBase.getCartItemDao().addCartItem(cartItem)

        mDataBase.getCartItemDao().updateCartItem(
            mDataBase.getCartItemDao().getCartItemById(1)[0].copy(name = "ze3ter")
        )

        assertEquals(
            mDataBase.getCartItemDao().getCartItemById(1)[0].name,
            "ze3ter"
        )
    }
    @Test
    fun testEmptyCart(){
        val cart = Cart(1, 1, 1)
        val cartItem = CartItem(1,"je3fer",1,1.0,1,1)
        val cartItem2 = CartItem(2,"je3fer2",1,1.0,1,1)
        mDataBase.getCartDao().addCart(cart)
        mDataBase.getCartItemDao().addCartItem(cartItem)
        mDataBase.getCartItemDao().addCartItem(cartItem2)
        mDataBase.getCartItemDao().emptyCart(1)
        assertTrue(mDataBase.getCartItemDao().getCartItemsbyCart(1).isEmpty())
    }


    @After
    fun closeDb(){
        mDataBase.close()
    }
}