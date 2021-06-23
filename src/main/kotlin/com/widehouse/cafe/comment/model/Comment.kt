package com.widehouse.cafe.comment.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "comment")
class Comment(
    @Id val id: String,
    @Indexed val articleId: String
)
