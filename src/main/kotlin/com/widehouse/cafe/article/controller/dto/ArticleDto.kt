package com.widehouse.cafe.article.controller.dto

data class ArticleDto(
    val id: String = "",
    val boards: List<String>,
    val title: String,
    val body: String
)
