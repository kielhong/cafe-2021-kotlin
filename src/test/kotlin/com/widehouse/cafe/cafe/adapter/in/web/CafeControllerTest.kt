package com.widehouse.cafe.cafe.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.application.port.`in`.CafeCreateUseCase
import com.widehouse.cafe.cafe.application.port.`in`.CafeQueryUseCase
import com.widehouse.cafe.common.exception.AlreadyExistException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.spring.SpringListener
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(CafeController::class)
internal class CafeControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var cafeQueryUseCase: CafeQueryUseCase

    @MockkBean
    private lateinit var cafeCreateUseCase: CafeCreateUseCase

    init {
        describe("Get cafe") {
            context("cafeService getCafe return cafe") {
                val cafe = CafeFixtures.create()
                every { cafeQueryUseCase.getCafe(any()) } returns Mono.just(cafe)

                it("should return cafe") {
                    webClient.get()
                        .uri("/cafe/{id}", cafe.id)
                        .exchange()
                        .expectStatus().isOk
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(cafe.id)
                        .jsonPath("$.name").isEqualTo(cafe.name)
                        .jsonPath("$.description").isEqualTo(cafe.description)
                }
            }
        }

        describe("Post cafe") {
            context("cafeService create return article") {
                val cafe = CafeFixtures.create()
                every { cafeCreateUseCase.create(cafe) } returns Mono.just(cafe)
                it("should return cafe and 200 OK") {
                    webClient.post()
                        .uri("/cafe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(cafe))
                        .exchange()
                        .expectStatus().isOk
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(cafe.id)

                    verify { cafeCreateUseCase.create(cafe) }
                }
            }
            context("cafeService create with already exist id") {
                val cafe = CafeFixtures.create()
                every { cafeCreateUseCase.create(cafe) } returns Mono.error(AlreadyExistException(cafe.id))
                it("should 409 conflict") {
                    webClient.post()
                        .uri("/cafe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(cafe))
                        .exchange()
                        .expectStatus().isEqualTo(HttpStatus.CONFLICT)

                    verify { cafeCreateUseCase.create(cafe) }
                }
            }
        }

        describe("GET cafe list by theme - /cafe?theme=any") {
            context("cafeService listCafe by category return cafes") {
                val cafe1 = CafeFixtures.create("1", "name1", "desc1", "movie")
                val cafe2 = CafeFixtures.create("2", "name2", "desc2", "movie")
                every { cafeQueryUseCase.listByTheme("movie") } returns Flux.just(cafe1, cafe2)
                it("should return cafes") {
                    webClient.get()
                        .uri("/cafe?theme=movie")
                        .exchange()
                        .expectStatus().isOk
                }
            }
        }
    }
}
