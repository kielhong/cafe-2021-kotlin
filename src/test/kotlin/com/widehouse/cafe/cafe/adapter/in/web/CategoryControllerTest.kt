package com.widehouse.cafe.cafe.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.cafe.cafe.application.port.`in`.CategoryQueryUseCase
import com.widehouse.cafe.cafe.domain.Category
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.spring.SpringListener
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@WebFluxTest(CategoryController::class)
class CategoryControllerTest(
    @Autowired val webClient: WebTestClient
) : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    override fun listeners() = listOf(SpringListener)

    @MockkBean
    private lateinit var categoryQueryUseCase: CategoryQueryUseCase

    init {
        describe("GET /categories") {
            val categories = (1L..5L).map { Category(it, "name$it") }
            every { categoryQueryUseCase.listCategories() } returns Flux.fromIterable(categories)
            it("should list all categories") {
                webClient
                    .get()
                    .uri("/categories")
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.size()").isEqualTo(5)
            }
        }
    }
}
