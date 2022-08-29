package com.widehouse.cafe.comment.application

import com.widehouse.cafe.comment.adapter.`in`.web.dto.CommentRequest
import com.widehouse.cafe.comment.domain.Comment
import reactor.core.publisher.Mono

interface CommentCommandUseCase {
    fun createComment(request: CommentRequest): Mono<Comment>
}
