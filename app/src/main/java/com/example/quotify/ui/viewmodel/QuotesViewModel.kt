package com.example.quotify.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotify.model.Result
import com.example.quotify.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesViewModel(val quoteRepository: QuoteRepository) : ViewModel() {

    private var quotes = mutableListOf<Result>()
    var quote: MutableLiveData<Result> = MutableLiveData()
    private var position: Int = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val quotes = quoteRepository.getQuotes(2)
            if (quotes != null) {
                this@QuotesViewModel.quotes = quotes.results.orEmpty().toMutableList()
                quote.postValue(this@QuotesViewModel.quotes.get(position))
            }
        }
    }

    fun onNext() {
        if (position == quotes.size - 1) {
            return
        }
        position++
        quote.postValue(this@QuotesViewModel.quotes.get(position))
    }

    fun onPrevious() {
        if (position == 0) {
            return
        }

        position--
        quote.postValue(this@QuotesViewModel.quotes.get(position))
    }

}
