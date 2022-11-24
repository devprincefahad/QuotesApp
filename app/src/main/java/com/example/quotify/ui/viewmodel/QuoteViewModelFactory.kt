package com.example.quotify.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quotify.repository.QuoteRepository

class QuoteViewModelFactory(private val repository: QuoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuotesViewModel(repository) as T
    }
}