package com.example.fooddelieveryapp

data class OrderItem(
    val id:String,
    val restaurantName:String,
    val date:String,
    val totalPrice:Int,
    val status : String
)
