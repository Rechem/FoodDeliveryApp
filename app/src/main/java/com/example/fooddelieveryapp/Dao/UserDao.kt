package com.example.fooddelieveryapp.Dao

import UserAndCart
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("select * from users")
    fun getUsers():List<User>
    @Insert
    fun addUsers(vararg users:User)
    @Update
    fun updateUser(user:User)
    @Delete
    fun deleteUser(user:User)
}