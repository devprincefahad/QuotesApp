package com.example.quotify.model

data class QuoteList(
	val count: Int? = null,
	val totalPages: Int? = null,
	val lastItemIndex: Int? = null,
	val page: Int? = null,
	val totalCount: Int? = null,
	val results: List<Result>? = null
)


