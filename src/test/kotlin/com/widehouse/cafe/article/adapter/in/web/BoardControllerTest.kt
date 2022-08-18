package com.widehouse.cafe.article.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.adapter.`in`.web.dto.BoardRequest
import com.widehouse.cafe.article.application.BoardService
import com.widehouse.cafe.article.domain.Board
import com.widehouse.cafe.cafe.CafeFixtures
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.spring.SpringListener
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.BDDAssertions.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(BoardController::class)
internal class BoardControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    lateinit var boardService: BoardService

    val cafe = CafeFixtures.create()

    init {
        describe("GetBoard") {
            val boardId = "test"
            context("get board by boardId") {
                val board = BoardFixtures.create(boardId, cafe.id)
                every { boardService.getBoard(board.id) } returns Mono.just(board)
                it("should return board") {
                    // when
                    webClient
                        .get()
                        .uri("/boards/{boardId}", board.id)
                        .exchange()
                        .expectStatus().isOk
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(board.id)
                        .jsonPath("$.cafeId").isEqualTo(cafe.id)
                }
            }

            context("do not get board by boardId") {
                it("should return 404 Not Found") {
                    // given
                    every { boardService.getBoard(boardId) } returns Mono.empty()
                    // when
                    webClient.get()
                        .uri {
                            it.path("/boards/{boardId}")
                                .build(boardId)
                        }
                        .exchange()
                        .expectStatus().isNotFound
                }
            }
        }

        describe("listBoard by Cafe") {
            it("should return board lists") {
                // given
                val board1 = BoardFixtures.create("1", cafe.id)
                val board2 = BoardFixtures.create("2", cafe.id)
                every { boardService.listBoard(cafe.id) } returns Flux.just(board1, board2)
                // when
                webClient
                    .get()
                    .uri {
                        it.path("/boards")
                            .queryParam("cafeId", cafe.id)
                            .build()
                    }
                    .exchange()
                    .expectStatus().isOk
                    .expectBodyList(Board::class.java)
                    .consumeWith<ListBodySpec<Board>> { then(it.responseBody).containsExactly(board1, board2) }
            }
        }

        describe("post Board") {
            context("given request") {
                val request = BoardRequest(cafe.id, "board", 1)
                every { boardService.createBoard(any()) } returns Mono.just(BoardFixtures.create("test", request.cafeId))

                val response = webClient
                    .post()
                    .uri("/boards")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .exchange()
                it("200 OK") {
                    response
                        .expectStatus().isOk
                    verify { boardService.createBoard(request) }
                }
                it("return created board") {
                    response
                        .expectBody()
                        .jsonPath("$.id").isEqualTo("test")
                    verify { boardService.createBoard(request) }
                }
            }
        }
    }
}
