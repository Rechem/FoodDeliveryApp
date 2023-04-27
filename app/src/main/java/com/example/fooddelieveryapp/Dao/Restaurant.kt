package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="restaurants" )
data class Restaurant(
    @PrimaryKey var restaurantId: Int?,
    var name:String?,
)
