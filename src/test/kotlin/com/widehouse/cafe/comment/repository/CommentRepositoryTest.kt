package com.widehouse.cafe.comment.repository

import com.widehouse.cafe.comment.model.Comment
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier
import java.util.UUID

@DataMongoTest
class CommentRepositoryTest @Autowired constructor(
    private val commentRepository: CommentRepository
) {
    @Test
    internal fun `articleId 가 주어지면 관련된 comment Flux 반환`() {
        val articleId = UUID.randomUUID().toString()
        // given
        commentRepository.save(Comment("1", articleId)).block()
        commentRepository.save(Comment("2", articleId)).block()
        // when
        val result = commentRepository.findByArticleId(articleId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it.articleId).isEqualTo(articleId) }
            .assertNext { then(it.articleId).isEqualTo(articleId) }
            .verifyComplete()
    }
}
