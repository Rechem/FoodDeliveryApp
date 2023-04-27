package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="users" )
data class User(
    @PrimaryKey
    var userId:Long? = null,
    var firstName:String?= null,
    var lastName:String? = null,
    val birthDate: Date?
)
