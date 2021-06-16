package com.widehouse.cafe.comment.service

import com.widehouse.cafe.comment.domain.Comment
import com.widehouse.cafe.comment.repository.CommentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class CommentService(
    private val commentRepository: CommentRepository
) {
    fun listComment(articleId: String): Flux<Comment> = commentRepository.findByArticleId(articleId)
}
