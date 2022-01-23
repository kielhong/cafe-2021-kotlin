package com.widehouse.cafe.comment.model

import java.time.ZonedDateTime

class Comment(
    val id: String,
    val articleId: String,
    val body: String,
    val createdAt: ZonedDateTime
)
