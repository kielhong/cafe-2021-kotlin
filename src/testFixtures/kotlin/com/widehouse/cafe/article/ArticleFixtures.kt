package com.widehouse.cafe.article

class ArticleFixtures {
    companion object {
        @JvmStatic
        fun create() = Article("id", listOf("board1Id", "board2Id"), "title", "body")
    }
}
