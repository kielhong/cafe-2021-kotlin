package com.widehouse.cafe.article.adapter.`in`.web.dto

import com.widehouse.cafe.article.domain.BoardType

data class BoardRequest(
    val cafeId: String,
    val name: String,
    val boardType: BoardType,
    val listOrder: Int
)
