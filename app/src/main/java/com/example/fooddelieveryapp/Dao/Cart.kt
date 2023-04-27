package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="carts" )
data class Cart(
    @PrimaryKey
    val cartid: Int,
    var retaurant:Long? = null,
//    var userId,
)
