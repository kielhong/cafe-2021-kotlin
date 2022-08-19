package com.widehouse.cafe.article

import com.widehouse.cafe.article.domain.Board

class BoardFixtures {
    companion object {
        fun create(id: String, cafeId: String, name: String = "board", listOrder: Int = 1) =
            Board(id, cafeId, name, listOrder)
    }
}
