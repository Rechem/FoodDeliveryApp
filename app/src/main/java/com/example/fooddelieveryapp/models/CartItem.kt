package com.example.fooddelieveryapp.models

import androidx.lifecycle.ViewModel

data class CartItem(
    val name:String,
    val image:String,
    val price:Int,
    val quantity:Int,
) : ViewModel()
