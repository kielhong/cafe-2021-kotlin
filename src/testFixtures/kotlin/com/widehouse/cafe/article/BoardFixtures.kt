package com.widehouse.cafe.article

import com.widehouse.cafe.article.domain.Board

class BoardFixtures {
    companion object {
        @JvmStatic
        fun create(id: String, cafeId: String) = Board(id, cafeId)
    }
}
