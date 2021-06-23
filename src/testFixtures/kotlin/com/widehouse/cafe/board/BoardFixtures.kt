package com.widehouse.cafe.board

import com.widehouse.cafe.model.Board

class BoardFixtures {
    companion object {
        @JvmStatic
        fun create(id: String, cafeId: String) = Board(id, cafeId)
    }
}
