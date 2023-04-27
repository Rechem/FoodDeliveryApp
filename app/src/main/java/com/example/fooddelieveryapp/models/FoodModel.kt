package com.example.fooddelieveryapp.models

import androidx.lifecycle.ViewModel

class FoodModel:ViewModel() {
    var food : Food? = null
    var restaurant : Restaurant? = null
    var userId : Int? = null
}