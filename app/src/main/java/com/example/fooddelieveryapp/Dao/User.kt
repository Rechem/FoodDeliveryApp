package com.example.fooddelieveryapp.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName ="users" )
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId:Long,
    var firstName:String,
    var lastName:String,
    val birthDate: Date?
)
