package com.widehouse.cafe.article

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import java.util.UUID

class ArticleTest {
    @Test
    fun testCreate() {
        val article = Article(id = UUID.randomUUID().toString(), boardId = "boardId", title = "title", body = "body")

        then(article)
            .hasFieldOrProperty("id")
            .hasFieldOrPropertyWithValue("boardId", "boardId")
            .hasFieldOrPropertyWithValue("title", "title")
            .hasFieldOrPropertyWithValue("body", "body")
    }
}
