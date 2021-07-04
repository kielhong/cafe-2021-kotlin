package com.widehouse.cafe.article

class ArticleFixtures {
    companion object {
        @JvmStatic
        fun create() = Article("id", "boardId", "title", "body")
    }
}
