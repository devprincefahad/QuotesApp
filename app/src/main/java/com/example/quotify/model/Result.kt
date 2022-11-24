package com.example.quotify.model

data class Result(
    val authorSlug: String? = null,
    val author: String? = null,
    val length: Int? = null,
    val dateModified: String? = null,
    val id: String? = null,
    val content: String? = null,
    val dateAdded: String? = null,
    val tags: List<String?>? = null
)
