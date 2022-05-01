package com.widehouse.cafe.comment.adapter.`in`.web

import com.widehouse.cafe.comment.CommentFixtures
import com.widehouse.cafe.comment.application.CommentQueryUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import java.util.UUID

@WebFluxTest(CommentController::class)
class CommentControllerTest(@Autowired val webClient: WebTestClient) {
    @MockBean
    private lateinit var commentQueryUseCase: CommentQueryUseCase

    private lateinit var articleId: String

    @BeforeEach
    fun setUp() {
        articleId = UUID.randomUUID().toString()
    }

    @Test
    fun `article에 딸린 댓글(Comment) 전체 목록을 작성 시간 desc 순으로 반환`() {
        // given
        val comment1 = CommentFixtures.create(UUID.randomUUID().toString())
        val comment2 = CommentFixtures.create(UUID.randomUUID().toString())
        given(commentQueryUseCase.listComments(articleId)).willReturn(Flux.just(comment1, comment2))
        // when
        webClient.get()
            .uri("/article/{articleId}/comment", articleId)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.size()").isEqualTo(2)
            .jsonPath("$.[0].id").isEqualTo(comment1.id)
            .jsonPath("$.[1].id").isEqualTo(comment2.id)
    }
}
