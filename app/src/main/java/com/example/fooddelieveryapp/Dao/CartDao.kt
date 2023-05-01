package com.example.fooddelieveryapp.Dao

import UserAndCart
import androidx.room.*

@Dao
interface CartDao {

    @Query("select * from carts where cartId=:id")
    fun getCartById(id:Int):List<Cart>

    @Query("select * from carts where ownerId=:userId and restaurantId=:restaurantId")
    fun getCartByUserIdAndRestaurantId(userId:Int,restaurantId:Int):List<Cart>
    @Insert
    fun addCart(cart:Cart):Long
    @Update
    fun updateCart(cart:Cart)
    @Delete
    fun deleteCart(cart:Cart)
}