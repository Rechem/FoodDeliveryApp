package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="restaurants" )
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    val restaurantId: Int?,
    var name:String?,
){
    constructor(name:String?) : this(0,name)
}
