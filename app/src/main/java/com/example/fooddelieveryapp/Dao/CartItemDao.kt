package com.example.fooddelieveryapp.Dao

import UserAndCart
import androidx.room.*

@Dao
interface CartItemDao {
    @Transaction
    @Query("select * from cartItems where cartId=:cartId")
    fun getCartItemsByCart(cartId:Int):List<CartItem>
    @Query("select * from cartItems where cartItemId=:id")
    fun getCartItemById(id:Int):List<CartItem>
    @Query("select * from cartItems")
    fun getAllItems():List<CartItem>
    @Insert
    fun addCartItem(vararg item:CartItem)
    @Update
    fun updateCartItem(item:CartItem)
    @Delete
    fun deleteCartItem(item:CartItem)
    @Query("delete from cartItems where cartId=:cartId")
    fun emptyCart(cartId:Int)
}