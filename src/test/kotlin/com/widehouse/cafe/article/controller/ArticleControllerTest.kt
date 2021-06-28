package com.widehouse.cafe.article.controller

import com.widehouse.cafe.article.ArticleFixtures
import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.model.Article
import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.service.ArticleService
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
internal class ArticleControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var articleService: ArticleService

    lateinit var cafe: Cafe
    lateinit var board: Board

    @BeforeEach
    internal fun setUp() {
        cafe = CafeFixtures.create()
        board = BoardFixtures.create(UUID.randomUUID().toString(), cafe.id)
    }

    @Nested
    @DisplayName("Get a Article")
    inner class GetArticle {
        private val article = ArticleFixtures.create()
        @Test
        fun `articleId에 해당하는 Article 반환`() {
            // given
            given(articleService.getArticle(article.id!!)).willReturn(Mono.just(article))
            // when
            webClient.get()
                .uri("/article/{articleId}", article.id)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(article.id!!)
        }

        @Test
        fun `articleId에 해당하는 Article이 없으면 404 NotFound`() {
            // given
            given(articleService.getArticle(article.id!!)).willReturn(Mono.empty())
            // when
            webClient.get()
                .uri("/article/{articleId}", article.id)
                .exchange()
                .expectStatus().isNotFound
        }
    }

    @Nested
    @DisplayName("Get Articles by Board")
    inner class ListArticleByBoard {
        @Test
        fun `list article by Board`() {
            // given
            given(articleService.listByBoard(board.id))
                .willReturn(
                    Flux.just(
                        Article("1", board.id, "title1", "body1"),
                        Article("2", board.id, "title2", "body2")
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

    @Nested
    @DisplayName("Get Articles by Cafe")
    inner class ListArticleByCafe {
        @Test
        fun `list article by Cafe`() {
            // given
            given(articleService.listByCafe(cafe.id))
                .willReturn(Flux.just(Article("1", "board1", "title1", "body1"), Article("2", "board2", "ttle2", "body2")))
            // when
            webClient.get()
                .uri("/article?cafeId={cafeId}", cafe.id)
                .exchange()
                .expectStatus().isOk
        }
    }

    @Nested
    @DisplayName("Create Article")
    inner class CreateArticle {
        @Test
        fun `create article then ok`() {
            // given
            val request = Article(boardId = "board", title = "title", body = "body")
            val article = Article("articleId", request.boardId, request.title, request.body)
            given(articleService.create(request)).willReturn(Mono.just(article))
            // when
            webClient.post()
                .uri("/article")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(article.id!!)
        }
    }
}
