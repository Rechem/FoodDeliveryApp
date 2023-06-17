package com.example.fooddelieveryapp.models

data class DetailedOrder(val idOrder: Int, val status: String, val date : String,
                         val restaurantName : String, val totalPrice : Int,
                         val meals : List<OrderMeal>,
                         val deliverer : Deliverer)