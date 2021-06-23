package com.widehouse.cafe.controller

import com.widehouse.cafe.model.Board
import com.widehouse.cafe.cafe.model.Cafe
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
        val cafeId = cafe.id
        val boardId = "1234"
        given(boardService.getBoard(cafeId, boardId)).willReturn(Mono.just(Board(boardId, cafeId)))
        // when
        webClient.get()
            .uri("/cafe/{url}/board/{id}", cafeId, boardId)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(boardId)
            .jsonPath("$.cafeUrl").isEqualTo(cafeId)
    }

    @Test
    fun given_empty_when_get_then_404NotFound() {
        // given
        val cafeId = cafe.id
        val boardId = "1234"
        given(boardService.getBoard(cafeId, boardId)).willReturn(Mono.empty())
        // when
        webClient.get()
            .uri("/cafe/{url}/board/{id}", cafeId, boardId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun given_cafeUrl_when_listByCafe_then_listBoards() {
        // given
        val cafeId = cafe.id
        val board1 = Board("1", cafeId)
        val board2 = Board("2", cafeId)
        given(boardService.listBoard(cafeId)).willReturn(Flux.just(board1, board2))
        // when
        webClient.get()
            .uri("/cafe/{url}/board", cafeId)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Board::class.java)
            .consumeWith<ListBodySpec<Board>> { then(it.responseBody).containsExactly(board1, board2) }
    }
}
