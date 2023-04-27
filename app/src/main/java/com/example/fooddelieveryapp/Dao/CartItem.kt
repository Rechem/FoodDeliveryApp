package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="cartitems" )
data class CartItem(
    @PrimaryKey
    val cartitemid: Int,
    val name:String,
    val image:Int,
    val price:Int,
    val quantity:Int,
)
