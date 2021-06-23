package com.widehouse.cafe.comment

import com.widehouse.cafe.comment.model.Comment
import java.util.UUID

class CommentFixtures {
    companion object {
        @JvmStatic
        fun create(id: String, articleId: String) = Comment(id, articleId)
        @JvmStatic
        fun create(articleId: String) = create(UUID.randomUUID().toString(), articleId)
    }
}
