package com.widehouse.cafe.article

import com.widehouse.cafe.article.domain.Board
import com.widehouse.cafe.article.domain.BoardType
import java.time.LocalDateTime

class BoardFixtures {
    companion object {
        fun create(
            id: String,
            cafeId: String,
            name: String = "board",
            boardType: BoardType = BoardType.Board,
            listOrder: Int = 1,
            createdAt: LocalDateTime = LocalDateTime.now()
        ) =
            Board(id, cafeId, name, boardType, listOrder, createdAt)
    }
}
