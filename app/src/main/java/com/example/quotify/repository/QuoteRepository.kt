package com.example.quotify.repository

import androidx.lifecycle.MutableLiveData
import com.example.quotify.api.QuoteInterface
import com.example.quotify.model.QuoteList

class QuoteRepository(private val quoteInterface: QuoteInterface) {

    private val quotesLiveData = MutableLiveData<QuoteList>()
//    val quotes: LiveData<QuoteList>
//        get() = quotesLiveData

    suspend fun getQuotes(page: Int): QuoteList? {
        val result = quoteInterface.getQuotes(page)
        if (result != null && result.body() != null) {
            quotesLiveData.postValue(result.body())
        }
        return result.body()
    }

}

