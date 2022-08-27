package com.widehouse.cafe.article.adapter.`in`.web

import com.ninjasquad.springmockk.MockkBean
import com.widehouse.cafe.article.Article
import com.widehouse.cafe.article.ArticleFixtures
import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.adapter.`in`.web.dto.ArticleRequest
import com.widehouse.cafe.article.application.ArticleService
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.common.exception.DataNotFoundException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.spring.SpringListener
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime.now
import java.util.UUID

@WebFluxTest(ArticleController::class)
internal class ArticleControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean
    lateinit var articleService: ArticleService

    private val cafe = CafeFixtures.create()
    private val board = BoardFixtures.create(UUID.randomUUID().toString(), cafe.id)

    init {
        describe("GetArticle") {
            val article = ArticleFixtures.create()

            it("articleId에 해당하는 Article 반환") {
                // given
                every { articleService.getArticle(article.id) } returns Mono.just(article)
                // when
                webClient.get()
                    .uri("/articles/{articleId}", article.id)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(article.id)
            }

            it("articleId에 해당하는 Article이 없으면 404 NotFound") {
                // given
                every { articleService.getArticle(article.id) } returns Mono.empty()
                // when
                webClient.get()
                    .uri("/articles/{articleId}", article.id)
                    .exchange()
                    .expectStatus().isNotFound
            }
        }

        describe("Get Articles by Board") {
            it("list article by Board") {
                // given
                every { articleService.listByBoard(board.id) }
                    .returns(
                        Flux.just(
                            Article("1", board.id, "title1", "body1", now()),
                            Article("2", board.id, "title2", "body2", now())
                        )
                    )
                // when
                webClient.get()
                    .uri("/articles?boardId={boardId}", board.id)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.size()").isEqualTo(2)
            }
        }

        describe("Get Articles by Cafe") {
            it("list article by Cafe") {
                // given
                every { articleService.listByCafe(cafe.id) }
                    .returns(
                        Flux.just(
                            Article("1", "board1", "title1", "body1", now()),
                            Article("2", "board2", "title2", "body2", now())
                        )
                    )
                // when
                webClient.get()
                    .uri("/articles?cafeId={cafeId}", cafe.id)
                    .exchange()
                    .expectStatus().isOk
            }
        }

        describe("Create Article") {
            context("POST /articles") {
                val article = ArticleFixtures.create()
                every { articleService.create(any()) } returns Mono.just(article)

                val request = ArticleRequest(board.id, "title", "body")
                val response = webClient.post()
                    .uri("/articles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .exchange()
                it("200 OK") {
                    response.expectStatus().isOk
                    verify { articleService.create(request) }
                }
                it("return created article id") {
                    response
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(article.id)
                }
            }
        }

        describe("Update Article") {
            val article = ArticleFixtures.create()
            val request = ArticleRequest("new-boardId", "new-title", "new-body")

            context("exist article") {
                every { articleService.update(article.id, request) } returns Mono.just(Article(article.id, request.boardId, request.title, request.body, now()))
                it("then update ok") {
                    webClient.put()
                        .uri {
                            it.path("/articles/{articleId}")
                                .build(article.id)
                        }
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(request))
                        .exchange()
                        .expectStatus().isOk
                        .expectBody()
                        .jsonPath("$.id").isEqualTo(article.id)

                    verify { articleService.update(article.id, request) }
                }
            }

            context("not exist article") {
                every { articleService.update(article.id, request) } returns Mono.error(DataNotFoundException(article.id))
                it("404 error") {
                    webClient.put()
                        .uri {
                            it.path("/articles/{articleId}")
                                .build(article.id)
                        }
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(request))
                        .exchange()
                        .expectStatus().isNotFound
                }
            }
        }

        describe("Delete Article") {
            val article = ArticleFixtures.create()
            context("article exists") {
                every { articleService.delete(any()) } returns Mono.empty()

                val response = webClient.delete()
                    .uri("/articles/{articleId}", article.id)
                    .exchange()
                it("200 OK") {
                    response.expectStatus().isOk
                    verify { articleService.delete(article.id) }
                }
            }
            context("article not exists") {
                every { articleService.delete(any()) } returns Mono.error(DataNotFoundException(article.id))
                val response = webClient.delete()
                    .uri("/articles/{articleId}", article.id)
                    .exchange()
                it("404 NotFound") {
                    response.expectStatus().isNotFound
                }
            }
        }
    }
}
