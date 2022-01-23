package com.widehouse.cafe.comment.controller

import com.widehouse.cafe.comment.model.Comment
import com.widehouse.cafe.comment.usecase.CommentQueryUseCase
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
