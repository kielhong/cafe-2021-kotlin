package com.widehouse.cafe.article

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "article")
data class Article(
    @Id
    val id: String,
    @Indexed
    val boardId: String,
    val title: String,
    val body: String
)
