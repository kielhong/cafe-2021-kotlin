package com.widehouse.cafe.comment.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.cafe.comment.CommentFixtures
import com.widehouse.cafe.comment.application.CommentQueryUseCase
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.spring.SpringListener
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import java.util.UUID

@WebFluxTest(CommentController::class)
class CommentControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    private lateinit var commentQueryUseCase: CommentQueryUseCase

    private val articleId = UUID.randomUUID().toString()

    init {
        describe("GET comments by articleId") {
            context("commentQueryUseCase list comments by articleId") {
                val comment1 = CommentFixtures.create(UUID.randomUUID().toString())
                val comment2 = CommentFixtures.create(UUID.randomUUID().toString())
                every { commentQueryUseCase.listComments(articleId) } returns Flux.just(comment1, comment2)

                val response = webClient.get()
                    .uri {
                        it.path("/comments")
                            .queryParam("articleId", articleId)
                            .build()
                    }
                    .exchange()
                it("200 OK") {
                    response.expectStatus().isOk
                }
                it("list comment order by createdAt desc") {
                    response
                        .expectBody()
                        .jsonPath("$.size()").isEqualTo(2)
                        .jsonPath("$.[0].id").isEqualTo(comment1.id)
                        .jsonPath("$.[1].id").isEqualTo(comment2.id)
                }
            }
        }
    }
}
