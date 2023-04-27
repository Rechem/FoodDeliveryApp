package com.example.fooddelieveryapp.Dao

import UserAndCart
import androidx.room.*

@Dao
interface CartItemDao {
    @Transaction
    @Query("select * from cartItems where cartId=:cartId")
    fun getCartItemsbyCart(cartId:Int):List<CartItem>
    @Insert
    fun addCartItem(vararg item:CartItem)
    @Update
    fun updateCartItem(item:CartItem)
    @Delete
    fun deleteCartItem(item:CartItem)
}