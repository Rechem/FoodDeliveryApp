package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="cartItems" )
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val cartItemId: Int,
    var name:String,
    var image:String,
    var price:Int,
    var quantity:Int,
    var cartId : Int
)
