package com.widehouse.cafe.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "board")
data class Board(
    @Id val id: String,
    @Indexed val cafeUrl: String
)
