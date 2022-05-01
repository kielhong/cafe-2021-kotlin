package com.widehouse.cafe.comment.adapter.`in`.web

import com.widehouse.cafe.comment.application.CommentQueryUseCase
import com.widehouse.cafe.comment.domain.Comment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/article/{articleId}/comment")
class CommentController(private val commentQueryUseCase: CommentQueryUseCase) {

    @GetMapping
    fun listComment(@PathVariable articleId: String): Flux<Comment> =
        commentQueryUseCase.listComments(articleId)
}
