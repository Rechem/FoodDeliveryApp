package com.example.fooddelieveryapp.models


data class Food(
    val idRestaurant:Int, val idMeal : Int,val picture: String,
    val name: String, val price: Int,val description: String)
