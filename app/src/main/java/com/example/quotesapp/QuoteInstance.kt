package com.example.quotesapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuoteInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://type.fit/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(QuoteInterface::class.java)
}
