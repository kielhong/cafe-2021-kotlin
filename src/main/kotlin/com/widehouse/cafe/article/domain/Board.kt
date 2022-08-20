package com.widehouse.cafe.article.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "board")
data class Board(
    @Id val id: String,
    @Indexed val cafeId: String,
    val name: String,
    val boardType: BoardType,
    val listOrder: Int,
    val createdAt: LocalDateTime
)
