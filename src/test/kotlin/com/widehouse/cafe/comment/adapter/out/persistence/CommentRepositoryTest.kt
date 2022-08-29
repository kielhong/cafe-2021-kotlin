package com.widehouse.cafe.comment.adapter.out.persistence

import com.widehouse.cafe.common.config.MongoConfig
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import reactor.test.StepVerifier
import java.time.LocalDateTime.now
import java.util.UUID

@DataMongoTest
@Import(MongoConfig::class)
class CommentRepositoryTest @Autowired constructor(
    private val commentRepository: CommentRepository
) {
    private val articleId = UUID.randomUUID().toString()

    @Test
    fun `articleId 가 주어지면 관련된 comment Flux 반환`() {
        // given
        commentRepository.save(CommentEntity("1", articleId, "test", now())).block()
        commentRepository.save(CommentEntity("2", articleId, "test", now())).block()
        // when
        val result = commentRepository.findByArticleId(articleId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it.articleId).isEqualTo(articleId) }
            .assertNext { then(it.articleId).isEqualTo(articleId) }
            .verifyComplete()
    }
}
