package com.example.fooddelieveryapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.fooddelieveryapp.Dao.*
import com.example.fooddelieveryapp.models.Food
import java.lang.IndexOutOfBoundsException

class CartModel(val cartItemDao : CartItemDao){
//    fun createCart(cartDao: CartDao,restaurantId : Int, ownerId : Int):Long{
//        return cartDao.addCart(Cart(0,ownerId,restaurantId))
//    }


    fun clearCart(){
        cartItemDao.emptyCart();
    }
    @Throws(Exception::class)
    fun addItemToCart(food : Food, qty : Int) {
    //check already present restaurant id
    val restaurantId = cartItemDao.getCartRestaurantId();
    if (restaurantId == 0 || restaurantId == food.idRestaurant) {
        cartItemDao.addCartItem(
            CartItem(
                food.idMeal,
                food.name,
                food.picture,
                food.price,
                qty,
                food.idRestaurant
            )
        )
    } else {
        throw Exception("Cannot add an item to the cart from a different restaurant");
    }
}

//    fun checkIfCartExists(cartDao: CartDao,restaurantId:Int,userId:Int):Boolean{
//        val results = cartDao.getCartByUserIdAndRestaurantId(userId = userId,restaurantId = restaurantId)
//        return results.isNotEmpty()
//    }

    fun getCartItems():List<CartItem>{
        try {
            return cartItemDao.getAllItems();
        }catch (e : IndexOutOfBoundsException){
            return emptyList()
        }
    }
}