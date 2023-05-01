package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="cartItems" )
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val cartItemId: Int,
    var name:String,
    var image:Int,
    var price:Double,
    var quantity:Int,
    var cartId : Int
)
