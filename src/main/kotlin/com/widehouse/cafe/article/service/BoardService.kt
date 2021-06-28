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
    fun getBoard(boardId: String): Mono<Board> = boardRepository.findById(boardId)

    @Transactional(readOnly = true)
    fun listBoard(cafeId: String): Flux<Board> {
        return boardRepository.findByCafeId(cafeId)
    }
}
