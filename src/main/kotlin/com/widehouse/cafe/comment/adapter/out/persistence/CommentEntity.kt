package com.widehouse.cafe.comment.adapter.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document(collection = "comment")
class CommentEntity(
    @Id
    val id: String,
    @Indexed
    val articleId: String,
    val body: String,
    val createdAt: ZonedDateTime
)
