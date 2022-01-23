package com.widehouse.cafe.comment.usecase

import com.widehouse.cafe.comment.model.Comment
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CommentQueryUseCase {
    fun listComments(articleId: String): Flux<Comment>
    fun getComment(id: String): Mono<Comment>
}
