package com.widehouse.cafe.common.mapper

import com.widehouse.cafe.comment.adapter.out.persistence.CommentEntity
import com.widehouse.cafe.comment.domain.Comment

fun CommentEntity.toModel() = Comment(
    this.id,
    this.articleId,
    this.body,
    this.createdAt
)
