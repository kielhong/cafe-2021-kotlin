package com.widehouse.cafe.cafe.controller

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.service.CafeService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
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
            .uri("/cafe/test")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("test")
    }
}
