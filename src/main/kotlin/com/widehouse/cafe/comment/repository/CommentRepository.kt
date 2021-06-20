package com.widehouse.cafe.comment.repository

import com.widehouse.cafe.comment.domain.Comment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CommentRepository : ReactiveMongoRepository<Comment, String> {
    fun findByArticleId(articleId: String): Flux<Comment>
}
