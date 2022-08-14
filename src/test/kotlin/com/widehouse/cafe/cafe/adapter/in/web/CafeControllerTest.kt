package com.widehouse.cafe.cafe.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.application.port.`in`.CafeCommandUseCase
import com.widehouse.cafe.cafe.application.port.`in`.CafeQueryUseCase
import com.widehouse.cafe.common.exception.AlreadyExistException
import com.widehouse.cafe.common.exception.DataNotFoundException
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
    private lateinit var cafeCommandUseCase: CafeCommandUseCase

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
            context("cafeService create and return cafe") {
                val cafe = CafeFixtures.create()
                every { cafeCommandUseCase.create(cafe) } returns Mono.just(cafe)
                it("should return cafe and 200 OK") {
                    webClient.post()
                        .uri("/cafe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(cafe))
                        .exchange()
                        .expectStatus().isOk
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(cafe.id)

                    verify { cafeCommandUseCase.create(cafe) }
                }
            }
            context("cafeService create with already exist id") {
                val cafe = CafeFixtures.create()
                every { cafeCommandUseCase.create(cafe) } returns Mono.error(AlreadyExistException(cafe.id))
                it("should 409 conflict") {
                    webClient.post()
                        .uri("/cafe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(cafe))
                        .exchange()
                        .expectStatus().isEqualTo(HttpStatus.CONFLICT)

                    verify { cafeCommandUseCase.create(cafe) }
                }
            }
        }

        describe("PUT cafe") {
            context("cafeService update and return updated cafe") {
                val cafeId = "test"
                val updateRequest = CafeRequest(id = cafeId, name = "newName", description = "newDesc", categoryId = 2L)
                val updatedCafe = CafeFixtures.create(id = cafeId, name = updateRequest.name, description = updateRequest.description, categoryId = updateRequest.categoryId)
                every { cafeCommandUseCase.update(cafeId, updateRequest) } returns Mono.just(updatedCafe)

                it("should return cafe and 200 OK") {
                    webClient.put()
                        .uri { it.path("/cafe/{cafeId}").build(cafeId) }
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(updateRequest))
                        .exchange()
                        .expectStatus().isOk
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(cafeId)
                        .jsonPath("$.name").isEqualTo(updatedCafe.name)

                    verify { cafeCommandUseCase.update(cafeId, updateRequest) }
                }
            }
        }

        describe("Delete cafe") {
            val cafeId = "test"

            context("cafeService delete cafe by cafeId") {
                every { cafeCommandUseCase.remove(cafeId) } returns Mono.empty()

                it("should delete cafe") {
                    webClient.delete()
                        .uri {
                            it.path("/cafe/{cafeId}")
                                .build(cafeId)
                        }
                        .exchange()
                        .expectStatus().isOk
                    verify { cafeCommandUseCase.remove(cafeId) }
                }
            }

            context("cafeService throw DataNotFoundException") {
                every { cafeCommandUseCase.remove(cafeId) } returns Mono.error(DataNotFoundException(cafeId))

                it("should 404 Not Found") {
                    webClient.delete()
                        .uri {
                            it.path("/cafe/{cafeId}")
                                .build(cafeId)
                        }
                        .exchange()
                        .expectStatus().isNotFound
                    verify { cafeCommandUseCase.remove(cafeId) }
                }
            }
        }

        describe("GET cafe list by category") {
            context("cafeService listCafe by category return cafes") {
                val cafe1 = CafeFixtures.create("1", "name1", "desc1", 1L)
                val cafe2 = CafeFixtures.create("2", "name2", "desc2", 1L)
                every { cafeQueryUseCase.listByCategory(1L) } returns Flux.just(cafe1, cafe2)

                it("should return cafes") {
                    webClient.get()
                        .uri("/cafe?categoryId=1")
                        .exchange()
                        .expectStatus().isOk
                }
            }
        }
    }
}
