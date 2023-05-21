package com.example.fooddelieveryapp.Dao

import androidx.room.*

@Dao
interface CartItemDao {
    @Transaction
    @Query("select * from cartItems where mealId=:id")
    fun getCartItemById(id:Int):List<CartItem>
    @Query("select * from cartItems")
    fun getAllItems():List<CartItem>

    @Query("select distinct restaurantId from cartItems")
    fun getCartRestaurantId():Int
    @Insert
    fun addCartItem(vararg item:CartItem)
    @Update
    fun updateCartItem(item:CartItem)
    @Delete
    fun deleteCartItem(item:CartItem)
    @Query("delete from cartItems")
    fun emptyCart()
}