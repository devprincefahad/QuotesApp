package com.example.quotify.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuoteInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://quotable.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(QuoteInterface::class.java)
}
