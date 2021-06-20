package com.widehouse.cafe.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "article")
data class Article(
    @Id val id: String,
    @Indexed val boardId: String
)
