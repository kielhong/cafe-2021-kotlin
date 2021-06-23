package com.widehouse.cafe.controller

import com.widehouse.cafe.model.Board
import com.widehouse.cafe.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
class BoardController(private val boardService: BoardService) {
    @GetMapping("cafe/{cafeUrl}/board/{boardId}")
    fun getBoard(
        @PathVariable cafeUrl: String,
        @PathVariable boardId: String
    ): Mono<Board> {
        return boardService.getBoard(cafeUrl, boardId)
            .switchIfEmpty { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)) }
    }

    @GetMapping("cafe/{cafeUrl}/board")
    fun listBoard(@PathVariable cafeUrl: String) = boardService.listBoard(cafeUrl)
}
