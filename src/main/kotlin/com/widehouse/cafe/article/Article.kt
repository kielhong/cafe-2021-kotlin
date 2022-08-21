package com.widehouse.cafe.article

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "article")
data class Article(
    @Id
    val id: String,
    @Indexed
    val boardId: String,
    val title: String,
    val body: String,
    val createdAt: LocalDateTime
)
