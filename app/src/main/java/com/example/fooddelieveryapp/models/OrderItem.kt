package com.example.fooddelieveryapp.models

data class OrderItem(
    val id:Int,
    val restaurantName:String,
    val date:String,
    val totalPrice:Int,
    val status : String
)
