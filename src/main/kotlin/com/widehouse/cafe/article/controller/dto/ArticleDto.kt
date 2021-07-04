package com.widehouse.cafe.article.controller.dto

data class ArticleDto(
    val id: String = "",
    val boardId: String,
    val title: String,
    val body: String
)
