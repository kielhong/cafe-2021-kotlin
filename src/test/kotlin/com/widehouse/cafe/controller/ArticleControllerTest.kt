package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Article
import com.widehouse.cafe.service.ArticleService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(ArticleController::class)
internal class ArticleControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var articleService: ArticleService

    @Nested
    @DisplayName("Get a Article")
    inner class GetArticle {
        private val articleId = "1234"

        @Test
        fun `articleId에 해당하는 Article 반환`() {
            // given
            given(articleService.getArticle(articleId)).willReturn(Mono.just(Article(articleId, "board")))
            // when
            webClient.get()
                .uri("/article/{articleId}", articleId)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(articleId)
        }

        @Test
        fun `articleId에 해당하는 Article이 없으면 404 NotFound`() {
            // given
            given(articleService.getArticle(articleId)).willReturn(Mono.empty())
            // when
            webClient.get()
                .uri("/article/{articleId}", articleId)
                .exchange()
                .expectStatus().isNotFound
        }
    }

    @Nested
    @DisplayName("Get Articles by Board")
    inner class ArticleListByBoard {
        @Test
        fun `list article by Board`() {
            // given
            val boardId = "1234"
            given(articleService.listArticleByBoard(boardId))
                .willReturn(Flux.just(Article("1", boardId), Article("2", boardId)))
            // when
            webClient.get()
                .uri("/article?boardId={boardId}", boardId)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.size()").isEqualTo(2)
        }
    }

    @Nested
    @DisplayName("Get Articles by Cafe")
    inner class ArticleListByCafe {
        @Test
        fun `list article by Cafe`() {
            // given
            val cafeUrl = "url"
            given(articleService.listArticleByCafe(cafeUrl))
                .willReturn(Flux.just(Article("1", "board1"), Article("2", "board2")))
            // when
            webClient.get()
                .uri("/article?cafeUrl={cafeUrl}", cafeUrl)
                .exchange()
                .expectStatus().isOk
        }
    }
}
