package com.example.fooddelieveryapp.models

import androidx.lifecycle.ViewModel

data class CartItem(
    val name:String,
    val image:Int,
    val price:Double,
    val quantity:Int,
) : ViewModel()
