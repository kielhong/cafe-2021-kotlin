package com.widehouse.cafe.article.application

import com.widehouse.cafe.article.adapter.`in`.web.dto.BoardRequest
import com.widehouse.cafe.article.adapter.out.persistence.BoardRepository
import com.widehouse.cafe.article.domain.Board
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
    @Transactional(readOnly = true)
    fun getBoard(boardId: String): Mono<Board> =
        boardRepository.findById(boardId)

    @Transactional(readOnly = true)
    fun listBoard(cafeId: String): Flux<Board> =
        boardRepository.findByCafeId(cafeId)
            .sort(Comparator.comparingInt(Board::listOrder))

    @Transactional
    fun createBoard(request: BoardRequest): Mono<Board> =
        boardRepository.save(
            Board(UUID.randomUUID().toString(), request.cafeId, request.name, request.boardType, request.listOrder)
        )
}
