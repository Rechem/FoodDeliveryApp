package com.example.fooddelieveryapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddelieveryapp.Dao.AppDatabase
import com.example.fooddelieveryapp.Dao.CartItem
import com.example.fooddelieveryapp.Dao.CartItemDao
import com.example.fooddelieveryapp.models.CartItemModal
import com.example.fooddelieveryapp.utils.CartModel

class CartViewModel(): ViewModel() {
    val cartmodel = CartModel.getInstance();
    private var cartTotal =  MutableLiveData<Int>(0);
    val readCartTotal : LiveData<Int> get() = cartTotal

    companion object {
        @Volatile
        private var instance: CartViewModel? = null
        fun getInstance(): CartViewModel {
            return instance ?: synchronized(this) {
                instance ?: CartViewModel().also { instance = it }
            }
        }
    }

    fun addToCartTotal(input:Int){
        cartTotal.value = (cartTotal.value)?.plus(input)
    }

    fun subFromCartTotal(input:Int){
        cartTotal.value = (cartTotal.value)?.minus(input)
    }

    fun setCartTotal(input:Int){
        cartTotal.value = input;
    }

    fun getCartItems():List<CartItem>{
        return cartmodel.getCartItems();
    }


    fun incrementCartItemQuantity(id:Int){
        cartmodel.incrementCartItemQuantity(id);
    }

    fun decrementCartItemQuantity(id:Int){
        cartmodel.decrementCartItemQuantity(id);
    }

    fun clearCart(){
        cartmodel.clearCart();
        cartTotal.value = 0;
    }

    fun deleteCartItem(id:Int){
        cartmodel.deleteCartItemById(id);
    }

}