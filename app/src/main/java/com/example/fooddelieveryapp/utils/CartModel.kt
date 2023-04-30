package com.example.fooddelieveryapp.utils

import com.example.fooddelieveryapp.Dao.Cart
import com.example.fooddelieveryapp.Dao.CartDao
import com.example.fooddelieveryapp.Dao.CartItem
import com.example.fooddelieveryapp.Dao.CartItemDao
import com.example.fooddelieveryapp.models.Food

class CartModel {
    fun CreateCart(Dao : CartDao,restaurantId : Int, ownerId : Int){
        Dao.addCart(Cart(1,ownerId,restaurantId))
    }
    fun addItemToCart(Dao : CartItemDao, restaurantId : Int,cartRestaurantId : Int,cartId:Int, food : Food, qty : Int){
        if(restaurantId ==cartRestaurantId ){
            Dao.addCartItem(CartItem(food.hashCode(),food.name,food.image,food.price,qty,cartId))
        }
    }
}