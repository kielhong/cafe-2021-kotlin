package com.widehouse.cafe.comment.adapter.`in`.web

import com.widehouse.cafe.comment.adapter.`in`.web.dto.CommentRequest
import com.widehouse.cafe.comment.application.CommentCommandUseCase
import com.widehouse.cafe.comment.application.CommentQueryUseCase
import com.widehouse.cafe.comment.domain.Comment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("comments")
class CommentController(
    private val commentQueryUseCase: CommentQueryUseCase,
    private val commentCommandUseCase: CommentCommandUseCase
) {
    @GetMapping(params = ["articleId"])
    fun listComment(@RequestParam articleId: String): Flux<Comment> =
        commentQueryUseCase.listComments(articleId)

    @PostMapping
    fun createComment(@RequestBody request: CommentRequest): Mono<Comment> =
        commentCommandUseCase.createComment(request)
}
