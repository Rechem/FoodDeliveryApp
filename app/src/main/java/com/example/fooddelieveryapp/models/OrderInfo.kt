package com.example.fooddelieveryapp.models

data class OrderInfo(val cookNote : String?, val deliveryAddress : String, val deliveryNote : String,
val meals : List<Meal>)
