package com.example.fooddelieveryapp.Dao

import UserAndCart
import androidx.room.*

@Dao
interface CartDao {

    @Query("select * from carts where cartId=:id")
    fun getCartById(id:Int):List<Cart>
    @Query("select * from carts")
    fun getUserCarts():List<UserAndCart>
    @Insert
    fun addCart(vararg cart:Cart)
    @Update
    fun updateCart(cart:Cart)
    @Delete
    fun deleteCart(cart:Cart)
}