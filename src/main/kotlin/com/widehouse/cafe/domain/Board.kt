package com.widehouse.cafe.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "board")
data class Board(
    @Id var id: String? = null,
    @Indexed val cafeId: String )
