package com.widehouse.cafe.article

class ArticleFixtures {
    companion object {
        fun create() = Article("id", listOf("board1Id", "board2Id"), "title", "body")
    }
}
