package com.widehouse.cafe.article.controller

import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.service.BoardService
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
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
internal class BoardControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var boardService: BoardService

    lateinit var cafe: Cafe

    @BeforeEach
    fun setUp() {
        cafe = CafeFixtures.create()
    }

    @Nested
    inner class GetBoard {
        @Test
        fun given_boardId_when_get_then_returnBoard() {
            // given
            val board = BoardFixtures.create("test", cafe.id)
            given(boardService.getBoard(board.id)).willReturn(Mono.just(board))
            // when
            webClient.get()
                .uri("/board/{boardId}", board.id)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(board.id)
                .jsonPath("$.cafeId").isEqualTo(cafe.id)
        }

        @Test
        fun given_empty_when_get_then_404NotFound() {
            // given
            val boardId = "1234"
            given(boardService.getBoard(boardId)).willReturn(Mono.empty())
            // when
            webClient.get()
                .uri("/board/{boardId}", boardId)
                .exchange()
                .expectStatus().isNotFound
        }
    }

    @Test
    fun given_cafeUrl_when_listByCafe_then_listBoards() {
        // given
        val board1 = Board("1", cafe.id)
        val board2 = Board("2", cafe.id)
        given(boardService.listBoard(cafe.id)).willReturn(Flux.just(board1, board2))
        // when
        webClient.get()
            .uri("/board?cafeId={cafeId}", cafe.id)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Board::class.java)
            .consumeWith<ListBodySpec<Board>> { then(it.responseBody).containsExactly(board1, board2) }
    }
}
