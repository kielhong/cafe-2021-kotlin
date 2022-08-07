package com.widehouse.cafe.comment

import com.widehouse.cafe.comment.adapter.out.persistence.CommentEntity
import java.time.ZonedDateTime.now
import java.util.UUID

class CommentEntityFixtures {
    companion object {
        fun create(id: String, articleId: String) = CommentEntity(id, articleId, id + articleId, now())
        fun create(articleId: String) = create(UUID.randomUUID().toString(), articleId)
    }
}
