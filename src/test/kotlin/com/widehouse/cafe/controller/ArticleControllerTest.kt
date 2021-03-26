package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Article
import com.widehouse.cafe.service.ArticleService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@WebFluxTest(ArticleController::class)
internal class ArticleControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    lateinit var articleService: ArticleService

    @Test
    fun given_articleId_when_get_then_returnArticle() {
        // given
        val articleId = "1234"
        given(articleService.getArticle(anyString()))
            .willReturn(Mono.just(Article(articleId)))
        // when
        webClient.get()
            .uri("/article/{articleId}", articleId)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(articleId)
    }

    @Test
    fun given_emptyArticle_when_get_then_404NotFound() {
        // given
        val articleId = "1234"
        given(articleService.getArticle(anyString()))
            .willReturn(Mono.empty())
        // when
        webClient.get()
            .uri("/article/{articleId}", articleId)
            .exchange()
            .expectStatus().isNotFound
    }
}
