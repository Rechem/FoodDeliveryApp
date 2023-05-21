package com.example.fooddelieveryapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.fooddelieveryapp.Dao.*
import com.example.fooddelieveryapp.models.Food
import java.lang.IndexOutOfBoundsException

class CartModel(private val cartItemDao : CartItemDao){

    companion object {
        @Volatile
        private var instance: CartModel? = null

        fun getInstance(cartItemDao : CartItemDao): CartModel {
            return instance ?: synchronized(this) {
                instance ?: CartModel(cartItemDao).also { instance = it }
            }
        }
    }


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

    fun incrementCartItemQuantity(id:Int){
        cartItemDao.incrementCartItemQuantity(id);
    }
    fun decrementCartItemQuantity(id:Int){
        cartItemDao.decrementCartItemQuantity(id);
    }
}