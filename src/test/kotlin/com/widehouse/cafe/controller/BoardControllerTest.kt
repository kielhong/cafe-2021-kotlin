package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.domain.Cafe
import com.widehouse.cafe.service.BoardService
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(BoardController::class)
class BoardControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var boardService: BoardService

    lateinit var cafe: Cafe

    @BeforeEach
    fun setUp() {
        cafe = Cafe("test")
    }

    @Test
    fun given_boardId_when_get_then_returnBoard() {
        // given
        val cafeUrl = cafe.url
        val boardId = "1234"
        given(boardService.getBoard(cafeUrl, boardId)).willReturn(Mono.just(Board(boardId, cafeUrl)))
        // when
        webClient.get()
            .uri("/cafe/{url}/board/{id}", cafeUrl, boardId)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(boardId)
            .jsonPath("$.cafeUrl").isEqualTo(cafeUrl)
    }

    @Test
    fun given_empty_when_get_then_404NotFound() {
        // given
        val cafeUrl = cafe.url
        val boardId = "1234"
        given(boardService.getBoard(cafeUrl, boardId)).willReturn(Mono.empty())
        // when
        webClient.get()
            .uri("/cafe/{url}/board/{id}", cafeUrl, boardId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun given_cafeUrl_when_listByCafe_then_listBoards() {
        // given
        val cafeUrl = cafe.url
        val board1 = Board("1", cafeUrl)
        val board2 = Board("2", cafeUrl)
        given(boardService.listBoard(cafeUrl)).willReturn(Flux.just(board1, board2))
        // when
        webClient.get()
            .uri("/cafe/{url}/board", cafeUrl)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Board::class.java)
            .consumeWith<ListBodySpec<Board>> { then(it.responseBody).containsExactly(board1, board2) }
    }
}
