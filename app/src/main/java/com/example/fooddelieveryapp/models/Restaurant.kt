package com.example.fooddelieveryapp.models


data class Restaurant(
    val idRestaurant : Int,
    val name: String, val picture: String, val location: String, val restaurantType:String,
    var rating: Float, var reviews: Int, val phoneNumber: String, val email:String,
    val facebook: String, val instagram: String,val address:String)
