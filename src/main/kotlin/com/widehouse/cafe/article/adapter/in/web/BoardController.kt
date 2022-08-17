package com.widehouse.cafe.article.adapter.`in`.web

import com.widehouse.cafe.article.application.BoardService
import com.widehouse.cafe.article.domain.Board
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
@RequestMapping("boards")
class BoardController(private val boardService: BoardService) {
    @GetMapping("{boardId}")
    fun getBoard(@PathVariable boardId: String): Mono<Board> = boardService.getBoard(boardId)
        .switchIfEmpty { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)) }

    @GetMapping(params = ["cafeId"])
    fun listBoard(@RequestParam cafeId: String) =
        boardService.listBoard(cafeId)
}
