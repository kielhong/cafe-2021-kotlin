package com.widehouse.cafe.comment.service

import com.widehouse.cafe.comment.CommentFixtures
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

@ExtendWith(MockitoExtension::class)
internal class CommentServiceTest {
    private lateinit var service: CommentService
    @Mock
    private lateinit var commentRepository: CommentRepository

    private lateinit var articleId: String

    @BeforeEach
    internal fun setUp() {
        service = CommentService(commentRepository)

        articleId = UUID.randomUUID().toString()
    }

    @Test
    internal fun `articleId 가 주어지면 해당 게시물에 딸린 댓글 목록을 반환`() {
        // given
        val comment1 = CommentFixtures.create(articleId)
        val comment2 = CommentFixtures.create(articleId)
        given(commentRepository.findByArticleId(articleId)).willReturn(Flux.just(comment1, comment2))
        // when
        val result = service.listComment(articleId)
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it).isEqualTo(comment1) }
            .assertNext { then(it).isEqualTo(comment2) }
            .expectComplete()
            .verify()
    }
}
