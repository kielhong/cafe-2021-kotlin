package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.repository.BoardRepository
import com.widehouse.cafe.repository.CafeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BoardService(
    private val boardRepository: BoardRepository,
) {
    @Transactional(readOnly = true)
    fun getBoard(cafeUrl: String, boardId: String): Mono<Board> {
        return boardRepository.findById(boardId)
            .filter { it.cafeUrl == cafeUrl }
    }

    @Transactional(readOnly = true)
    fun listBoard(cafeUrl: String): Flux<Board> {
        return boardRepository.findByCafeUrl(cafeUrl)
    }
}