package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.service.BoardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class BoardController(private val boardService: BoardService) {
    @GetMapping("cafe/{cafeUrl}/board/{boardId}")
    fun getBoard(
        @PathVariable cafeUrl: String,
        @PathVariable boardId: String
    ): Mono<Board> {
        return boardService.getBoard(cafeUrl, boardId)
    }

    @GetMapping("cafe/{cafeUrl}/board")
    fun listBoard(@PathVariable cafeUrl: String): Flux<Board> {
        return boardService.listBoard(cafeUrl)
    }
}
