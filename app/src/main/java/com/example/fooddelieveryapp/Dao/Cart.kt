package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="carts" )
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int,
    val ownerId : Int?,
    val restaurantId : Int?
)
