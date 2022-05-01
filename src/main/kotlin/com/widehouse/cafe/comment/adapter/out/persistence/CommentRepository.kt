package com.widehouse.cafe.comment.adapter.out.persistence

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CommentRepository : ReactiveMongoRepository<CommentEntity, String> {
    fun findByArticleId(articleId: String): Flux<CommentEntity>
}
