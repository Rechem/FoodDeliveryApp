package com.example.fooddelieveryapp.models

data class Order(val idOrder : Int, val restaurantName : String?,
                 val date : String?, val status : String?, val totalPrice : Int)