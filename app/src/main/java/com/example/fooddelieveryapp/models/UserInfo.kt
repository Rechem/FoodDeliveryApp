package com.example.fooddelieveryapp.models

data class UserInfo(val idUser : Int, val username : String, val email: String,
    val phoneNumber : String, val address : String?, val avatar : String,
                         val token : String)