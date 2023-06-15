package com.example.fooddelieveryapp.Dao
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()
        return chain.proceed(modifiedRequest)
    }
}