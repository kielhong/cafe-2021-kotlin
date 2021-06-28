package com.widehouse.cafe.article.controller

import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
class BoardController(private val boardService: BoardService) {
    @GetMapping("cafe/{cafeId}/board/{boardId}")
    fun getBoard(
        @PathVariable cafeId: String,
        @PathVariable boardId: String
    ): Mono<Board> {
        return boardService.getBoard(cafeId, boardId)
            .switchIfEmpty { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)) }
    }

    @GetMapping("cafe/{cafeId}/board")
    fun listBoard(@PathVariable cafeId: String) = boardService.listBoard(cafeId)
}
