package com.example.fooddelieveryapp.utils

import android.content.Context
import androidx.room.Room
import com.example.fooddelieveryapp.Dao.*
import com.example.fooddelieveryapp.models.Food
import java.lang.IndexOutOfBoundsException

class CartModel{
    fun createCart(cartDao: CartDao,restaurantId : Int, ownerId : Int):Long{
        return cartDao.addCart(Cart(0,ownerId,restaurantId))
    }
    fun addItemToCart(cartItemDao: CartItemDao,restaurantId : Int,cartRestaurantId : Int,cartId:Int, food : Food, qty : Int){
        if(restaurantId ==cartRestaurantId ){
            cartItemDao.addCartItem(CartItem(0,food.name,food.image,food.price,qty,cartId))
        }
    }

    fun checkIfCartExists(cartDao: CartDao,restaurantId:Int,userId:Int):Boolean{
        val results = cartDao.getCartByUserIdAndRestaurantId(userId = userId,restaurantId = restaurantId)
        return results.isNotEmpty()
    }

    fun getCartItems(cartDao: CartDao,cartItemDao: CartItemDao,restaurantId:Int,userId:Int):List<CartItem>{
        try {
            return cartItemDao.getCartItemsByCart(
                cartDao.getCartByUserIdAndRestaurantId(
                    restaurantId,
                    userId
                )[0].cartId
            )
        }catch (e : IndexOutOfBoundsException){
            return emptyList()
        }
    }
}