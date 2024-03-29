package com.example.fooddelieveryapp.Dao

import android.content.Context
import android.util.Log
import com.example.fooddelieveryapp.models.Avatar
import com.example.fooddelieveryapp.models.DetailedOrder
import com.example.fooddelieveryapp.models.Food
import com.example.fooddelieveryapp.models.GoogleignInInfo
import com.example.fooddelieveryapp.models.Order
import com.example.fooddelieveryapp.models.OrderInfo
import com.example.fooddelieveryapp.models.Rating
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.fooddelieveryapp.models.Restaurant
import com.example.fooddelieveryapp.models.SignUpInfo
import com.example.fooddelieveryapp.models.UserConnexion
import com.example.fooddelieveryapp.models.UserInfo
import com.example.fooddelieveryapp.utils.API_URL
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Endpoint {

    companion object {
        var endpoint: Endpoint? = null
        fun createEndpoint(context : Context): Endpoint {
            val prefs = context.getSharedPreferences("connection", Context.MODE_PRIVATE)
            val token = prefs.getString("token","")!!
            Log.i("tokenRatez",token)
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(token))
                .build()
            endpoint = null;
            endpoint =
            Retrofit.Builder().baseUrl(API_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()
            ).build().create(Endpoint::class.java)
            return endpoint!!
        }
    }
    @POST("login")
    suspend fun login(@Body ConnexionInfo : UserConnexion): Response<UserInfo>

    @POST("signup")
    suspend fun signup(@Body signupInfo : SignUpInfo): Response<UserInfo>
    @Headers("Content-Type: application/json")
    @POST("orders")
    suspend fun order(@Body orderInfo : OrderInfo): Response<UserInfo>
    @Multipart
    @POST("users/avatar")
    suspend fun updateAvatar(@Part avatarImage: MultipartBody.Part): Response<Avatar>
    @Headers("Content-Type: application/json")
    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<Restaurant>>
    @Headers("Content-Type: application/json")
    @GET("orders")
    suspend fun getOrders(): Response<List<Order>>
    @GET("meals/restaurant/{idRestaurant}")
    suspend fun getMenus(@Path("idRestaurant") idRestaurant:Int): Response<List<Food>>
    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") id:Int): Response<DetailedOrder>
    @Headers("Content-Type: application/json")
    @POST("restaurants/rate")
    suspend fun rate(@Body rating : Rating): Response<Unit>
    @POST("loginWithGoogle")
    suspend fun googleSignIn(@Body info : GoogleignInInfo): Response<UserInfo>
}