package com.widehouse.cafe.comment.application

import com.widehouse.cafe.comment.adapter.`in`.web.dto.CommentRequest
import com.widehouse.cafe.comment.adapter.out.persistence.CommentEntity
import com.widehouse.cafe.comment.adapter.out.persistence.CommentRepository
import com.widehouse.cafe.comment.domain.Comment
import com.widehouse.cafe.common.mapper.toModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@Service
class CommentCommandService(
    private val commentRepository: CommentRepository
) : CommentCommandUseCase {
    override fun createComment(request: CommentRequest): Mono<Comment> =
        commentRepository.save(
            CommentEntity(UUID.randomUUID().toString(), request.articleId, request.body, LocalDateTime.now())
        )
            .map { it.toModel() }
}