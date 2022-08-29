package com.widehouse.cafe.comment.domain

import java.time.LocalDateTime

class Comment(
    val id: String,
    val articleId: String,
    val body: String,
    val createdAt: LocalDateTime
)
