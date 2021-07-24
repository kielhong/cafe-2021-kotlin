package com.widehouse.cafe.cafe.controller

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.service.CafeService
import com.widehouse.cafe.common.exception.AlreadyExistException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(CafeController::class)
internal class CafeControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var cafeService: CafeService

    @Test
    fun given_id_when_get_then_returnCafe() {
        // given
        val cafe = CafeFixtures.create()
        given(cafeService.getCafe(anyString())).willReturn(Mono.just(cafe))
        // when
        webClient.get()
            .uri("/cafe/{id}", cafe.id)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(cafe.id)
            .jsonPath("$.name").isEqualTo(cafe.name)
            .jsonPath("$.description").isEqualTo(cafe.description)
    }

    @Nested
    @DisplayName("Cafe 생성")
    inner class CafeCreate {
        @Test
        fun then_ok() {
            // given
            val cafe = CafeFixtures.create()
            given(cafeService.create(cafe)).willReturn(Mono.just(cafe))
            // when
            webClient.post()
                .uri("/cafe")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cafe))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(cafe.id)
            // then
            verify(cafeService).create(cafe)
        }

        @Test
        fun given_existId_then_409Conflict() {
            // given
            val cafe = CafeFixtures.create()
            given(cafeService.create(cafe)).willReturn(Mono.error(AlreadyExistException(cafe.id)))
            // when
            webClient.post()
                .uri("/cafe")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cafe))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
        }
    }

    @Nested
    @DisplayName("Theme 별 Cafe")
    inner class CafeByTheme {
        @Test
        fun give_theme_then_listCafe() {
            // given
            val cafe1 = CafeFixtures.create("1", "name1", "desc1", "movie")
            val cafe2 = CafeFixtures.create("2", "name2", "desc2", "movie")
            given(cafeService.listByTheme("movie")).willReturn(Flux.just(cafe1, cafe2))
            // when
            webClient.get()
                .uri("/cafe?theme=movie")
                .exchange()
                .expectStatus().isOk
        }
    }
}
