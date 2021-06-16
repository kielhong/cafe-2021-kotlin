package com.widehouse.cafe.comment.controller

import com.widehouse.cafe.comment.service.CommentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article/{articleId}/comment")
class CommentController(private val commentService: CommentService) {

    @GetMapping
    fun listComment(@PathVariable articleId: String) = commentService.listComment(articleId)
}
