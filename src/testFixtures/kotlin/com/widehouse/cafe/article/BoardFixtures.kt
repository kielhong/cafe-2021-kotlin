package com.widehouse.cafe.article

import com.widehouse.cafe.article.domain.Board
import com.widehouse.cafe.article.domain.BoardType

class BoardFixtures {
    companion object {
        fun create(id: String, cafeId: String, name: String = "board", boardType: BoardType = BoardType.Board, listOrder: Int = 1) =
            Board(id, cafeId, name, boardType, listOrder)
    }
}
