package com.example.fooddelieveryapp.Dao

import com.example.fooddelieveryapp.models.Food
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.fooddelieveryapp.models.Restaurant
import com.example.fooddelieveryapp.utils.API_URL

interface Endpoint {

    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                endpoint = Retrofit.Builder().baseUrl(API_URL). addConverterFactory(
                    GsonConverterFactory.create()).build(). create(Endpoint::class.java)

            }
            return endpoint!!
        }
    }

    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>
    @GET("meals/restaurant/{idRestaurant}")
    suspend fun getMenus(@Path("idRestaurant") idRestaurant:Int): Response<List<Food>>
}