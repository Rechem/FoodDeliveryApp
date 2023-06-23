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

    @Query("SELECT COUNT(1) FROM cartItems WHERE mealId = :id")
    fun checkMealExistsInCart(id: Int):Int

    @Insert
    fun addCartItem(vararg item:CartItem)
    @Query("update cartItems set quantity = quantity + 1 where mealId = :id")
    fun incrementCartItemQuantity(id: Int)

    @Query("update cartItems set quantity = quantity - 1 where mealId = :id")
    fun decrementCartItemQuantity(id: Int)
    @Query("delete FROM cartItems where mealId = :id")
    fun deleteCartItemById(id: Int)
    @Query("delete from cartItems")
    fun emptyCart()
}