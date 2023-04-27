package com.example.fooddelieveryapp.Dao
import androidx.room.Embedded
import androidx.room.Relation
import com.example.fooddelieveryapp.Dao.Cart
import com.example.fooddelieveryapp.Dao.User


data class RestaurantAndCart(
    @Embedded val restaurant: Restaurant,
    @Relation(
        parentColumn = "restaurantId",
        entityColumn = "restaurantId"
    )
    val cart: Cart
)
