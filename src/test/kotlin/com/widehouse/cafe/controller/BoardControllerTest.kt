package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.service.BoardService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(BoardController::class)
class BoardControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var boardService: BoardService

    @Test
    fun given_boardId_when_get_then_returnBoard() {
        // given
        given(boardService.getBoard("test", "1234"))
            .willReturn(Mono.just(Board("1234", "test")))
        // when
        webClient.get()
            .uri("/cafe/{url}/board/{id}", "test", "1234")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("1234")
            .jsonPath("$.cafeUrl").isEqualTo("test")
    }
}
