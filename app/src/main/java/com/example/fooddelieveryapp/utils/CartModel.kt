package com.example.fooddelieveryapp.utils

import com.example.fooddelieveryapp.Dao.*
import com.example.fooddelieveryapp.models.Food
import java.lang.IndexOutOfBoundsException

class CartModel(){

    private val cartItemDao = AppDatabase.getInstance(null)?.getCartItemDao()!!;

    companion object {
        @Volatile
        private var instance: CartModel? = null

        fun getInstance(): CartModel {
            return instance ?: synchronized(this) {
                instance ?: CartModel().also { instance = it }
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

    if(checkIfItemExists(food.idMeal)){
        throw Exception("This item is already in the cart");
    }

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

    fun checkIfItemExists(mealId: Int): Boolean {
        return cartItemDao.checkMealExistsInCart(mealId) == 1;
    }

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

    fun deleteCartItemById(id: Int){
        cartItemDao.deleteCartItemById(id);
    }
}