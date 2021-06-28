package com.widehouse.cafe.article.service

import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BoardService(
    private val boardRepository: BoardRepository,
) {
    @Transactional(readOnly = true)
    fun getBoard(cafeId: String, boardId: String): Mono<Board> {
        return boardRepository.findById(boardId)
            .filter { it.cafeId == cafeId }
    }

    @Transactional(readOnly = true)
    fun listBoard(cafeId: String): Flux<Board> {
        return boardRepository.findByCafeId(cafeId)
    }
}
