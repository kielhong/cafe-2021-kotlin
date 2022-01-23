package com.widehouse.cafe.comment.service

import com.widehouse.cafe.comment.CommentEntityFixtures.Companion.create
import com.widehouse.cafe.comment.repository.CommentRepository
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.UUID

// TODO : kotest 전환
@ExtendWith(MockitoExtension::class)
internal class CommentQueryServiceTest {
    private lateinit var service: CommentQueryService
    @Mock
    private lateinit var commentRepository: CommentRepository

    private val articleId = UUID.randomUUID().toString()
    private val comment1 = create(articleId)
    private val comment2 = create(articleId)

    @BeforeEach
    internal fun setUp() {
        service = CommentQueryService(commentRepository)
    }

    @Test
    internal fun `articleId 가 주어지면 해당 게시물에 딸린 댓글 목록을 반환`() {
        // given
        given(commentRepository.findByArticleId(articleId))
            .willReturn(Flux.just(comment1, comment2))
        // when
        val result = service.listComments(articleId)
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it.id).isEqualTo(comment1.id) }
            .assertNext { then(it.id).isEqualTo(comment2.id) }
            .expectComplete()
            .verify()
    }
}
