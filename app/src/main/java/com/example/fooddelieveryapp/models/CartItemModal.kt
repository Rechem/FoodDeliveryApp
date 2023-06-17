package com.example.fooddelieveryapp.models

import androidx.lifecycle.ViewModel

data class CartItemModal(
    val id:Int,
    val name:String,
    val image:String,
    val price:Int,
    var quantity:Int,
) : ViewModel()
