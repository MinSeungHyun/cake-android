package com.icicle.cake.util

import com.icicle.cake.models.CakeRoom
import com.icicle.cake.models.CakeUserWithToken
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val URL = "http://api.junhoyeo.xyz"
private val retrofit = Retrofit.Builder()
    .baseUrl(URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val retrofitService: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

interface RetrofitInterface {
    @POST("/auth/login")
    fun login(@Body param: HashMap<String, String>): Call<CakeUserWithToken>

    @GET("/room")
    fun getRooms(): Call<CakeRoom>

    @POST("/device/toggle")
    fun postScannedQR(@Body param: HashMap<String, String>): Call<Void>
}