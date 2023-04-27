package com.example.fooddelieveryapp.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface UserDao {
    @Query("select * from users where firstName=:firstName")
    fun getUsersByFirstName(firstName:String):List<User>
    @Insert
    fun addUsers(vararg users:User)
    @Update
    fun updateUser(user:User)
    @Delete
    fun deleteUser(user:User)
}