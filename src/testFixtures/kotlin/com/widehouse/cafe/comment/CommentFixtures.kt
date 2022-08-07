package com.widehouse.cafe.comment

import com.widehouse.cafe.comment.domain.Comment
import java.time.ZonedDateTime.now
import java.util.UUID

class CommentFixtures {
    companion object {
        fun create(id: String, articleId: String) = Comment(id, articleId, id + articleId, now())
        fun create(articleId: String) = create(UUID.randomUUID().toString(), articleId)
    }
}
