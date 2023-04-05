package com.example.fooddelieveryapp.models

data class Restaurant(
    val name: String, val logo: Int, val location: String, val cuisineType:String,
    var rating: Float, var reviews: Int, val phone: String, val email:String,
    val fbMob: String, val fbWeb: String, val igMob: String, val igWeb: String, val adresse:String)
