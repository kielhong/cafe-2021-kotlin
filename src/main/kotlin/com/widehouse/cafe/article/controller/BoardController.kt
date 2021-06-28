package com.widehouse.cafe.article.controller

import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
class BoardController(private val boardService: BoardService) {
    @GetMapping("board/{boardId}")
    fun getBoard(@PathVariable boardId: String): Mono<Board> = boardService.getBoard(boardId)
        .switchIfEmpty { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)) }

    @GetMapping("board", params = ["cafeId"])
    fun listBoard(@RequestParam cafeId: String) = boardService.listBoard(cafeId)
}
