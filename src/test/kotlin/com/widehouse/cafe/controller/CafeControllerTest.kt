package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Cafe
import com.widehouse.cafe.service.CafeService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(CafeController::class)
class CafeControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var cafeService: CafeService

    @Test
    fun given_url_when_get_then_returnCafe() {
        // given
        val cafe = Cafe("test")
        given(cafeService.getCafe(anyString()))
            .willReturn(Mono.just(cafe))
        // when
        webClient.get()
            .uri("/cafe/test")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.url").isEqualTo("test")
    }
}
