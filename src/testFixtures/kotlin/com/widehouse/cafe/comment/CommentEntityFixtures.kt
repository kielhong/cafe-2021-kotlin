package com.widehouse.cafe.comment

import com.widehouse.cafe.comment.adapter.out.persistence.CommentEntity
import java.time.ZonedDateTime.now
import java.util.UUID

class CommentEntityFixtures {
    companion object {
        @JvmStatic
        fun create(id: String, articleId: String) = CommentEntity(id, articleId, id + articleId, now())
        @JvmStatic
        fun create(articleId: String) = create(UUID.randomUUID().toString(), articleId)
    }
}
