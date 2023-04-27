package com.example.fooddelieveryapp.Dao

import androidx.room.Embedded
import androidx.room.Relation

data class CartAndCartItem(
    @Embedded val cart: Cart,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "cartId"
    )
    val cartItems: List<CartItem>
)