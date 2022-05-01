package com.widehouse.cafe.comment.application

import com.widehouse.cafe.comment.adapter.out.persistence.CommentRepository
import com.widehouse.cafe.comment.domain.Comment
import com.widehouse.cafe.common.mapper.toModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class CommentQueryService(
    private val commentRepository: CommentRepository
) : CommentQueryUseCase {
    override fun listComments(articleId: String): Flux<Comment> =
        commentRepository.findByArticleId(articleId)
            .map { it.toModel() }

    override fun getComment(id: String): Mono<Comment> {
        TODO("Not yet implemented")
    }
}
