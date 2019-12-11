package com.icicle.cake.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://api.dimigo.in"
private val retrofit = Retrofit.Builder()
    .baseUrl(URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val retrofitService = retrofit.create(RetrofitInterface::class.java)

interface RetrofitInterface