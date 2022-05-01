package com.widehouse.cafe.article.adapter.`in`.web.dto

data class ArticleDto(
    val id: String = "",
    val boards: List<String>,
    val title: String,
    val body: String
)
