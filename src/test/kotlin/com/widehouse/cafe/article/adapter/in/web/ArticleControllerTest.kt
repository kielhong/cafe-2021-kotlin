package com.widehouse.cafe.article.adapter.`in`.web

import com.widehouse.cafe.article.Article
import com.widehouse.cafe.article.ArticleFixtures
import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.adapter.`in`.web.dto.ArticleDto
import com.widehouse.cafe.article.application.ArticleService
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.common.exception.DataNotFoundException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.spring.SpringListener
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@WebFluxTest(ArticleController::class)
internal class ArticleControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    override fun listeners() = listOf(SpringListener)

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    lateinit var articleService: ArticleService

    private val cafe = CafeFixtures.create()
    private val board = BoardFixtures.create(UUID.randomUUID().toString(), cafe.id)

    init {
        describe("GetArticle") {
            val article = ArticleFixtures.create()

            it("articleId에 해당하는 Article 반환") {
                // given
                given(articleService.getArticle(article.id)).willReturn(Mono.just(article))
                // when
                webClient.get()
                    .uri("/article/{articleId}", article.id)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(article.id)
            }

            it("articleId에 해당하는 Article이 없으면 404 NotFound") {
                // given
                given(articleService.getArticle(article.id)).willReturn(Mono.empty())
                // when
                webClient.get()
                    .uri("/article/{articleId}", article.id)
                    .exchange()
                    .expectStatus().isNotFound
            }
        }

        describe("Get Articles by Board") {
            it("list article by Board") {
                // given
                given(articleService.listByBoard(board.id))
                    .willReturn(
                        Flux.just(
                            Article("1", listOf(board.id), "title1", "body1"),
                            Article("2", listOf(board.id), "title2", "body2")
                        )
                    )
                // when
                webClient.get()
                    .uri("/article?boardId={boardId}", board.id)
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.size()").isEqualTo(2)
            }
        }

        describe("Get Articles by Cafe") {
            it("list article by Cafe") {
                // given
                given(articleService.listByCafe(cafe.id))
                    .willReturn(
                        Flux.just(
                            Article("1", listOf("board1"), "title1", "body1"),
                            Article("2", listOf("board2"), "title2", "body2")
                        )
                    )
                // when
                webClient.get()
                    .uri("/article?cafeId={cafeId}", cafe.id)
                    .exchange()
                    .expectStatus().isOk
            }
        }

        describe("Create Article") {
            it("create article then ok") {
                // given
                val request = ArticleDto(boards = listOf("board"), title = "title", body = "body")
                val article = Article("articleId", request.boards, request.title, request.body)
                given(articleService.create(request)).willReturn(Mono.just(article))
                // when
                webClient.post()
                    .uri("/article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(article.id)
            }
        }

        describe("Update Article") {
            val article = ArticleFixtures.create()
            val request = ArticleDto(article.id, listOf("newBoardId"), "newTitle", "newBody")

            it("exist article then update ok") {
                // given
                given(articleService.update(request))
                    .willReturn(Mono.just(Article(article.id, request.boards, request.title, request.body)))
                // when
                webClient.put()
                    .uri("/article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .exchange()
                    .expectStatus().isOk
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(article.id)
            }

            it("not exist article then 404 error") {
                // given
                given(articleService.update(request))
                    .willReturn(Mono.error(DataNotFoundException(request.id)))
                // then
                webClient.put()
                    .uri("/article")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .exchange()
                    .expectStatus().isNotFound
            }
        }
    }
}
