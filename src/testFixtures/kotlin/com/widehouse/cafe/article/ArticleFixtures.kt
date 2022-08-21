package com.widehouse.cafe.article

import java.time.LocalDateTime

class ArticleFixtures {
    companion object {
        fun create() = Article("id", "board1Id", "title", "body", LocalDateTime.now())
    }
}
