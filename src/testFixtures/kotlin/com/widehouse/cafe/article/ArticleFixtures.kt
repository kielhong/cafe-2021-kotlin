package com.widehouse.cafe.article

import com.widehouse.cafe.article.model.Article

class ArticleFixtures {
    companion object {
        @JvmStatic
        fun create() = Article("id", "boardId", "title", "body")
    }
}
