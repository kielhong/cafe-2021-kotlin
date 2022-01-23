package com.widehouse.cafe.common.mapper

import com.widehouse.cafe.comment.model.Comment
import com.widehouse.cafe.comment.repository.CommentEntity

fun CommentEntity.toModel() = Comment(
    this.id,
    this.articleId,
    this.body,
    this.createdAt
)
