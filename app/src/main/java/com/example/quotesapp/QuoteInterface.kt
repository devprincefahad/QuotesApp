package com.example.quotesapp

import retrofit2.Call
import retrofit2.http.GET

interface QuoteInterface {

    @GET("api/quotes")
    fun callQuotes(): Call<List<QuotesResponse>>

}