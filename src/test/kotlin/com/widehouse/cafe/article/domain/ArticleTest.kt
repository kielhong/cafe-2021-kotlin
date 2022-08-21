package com.widehouse.cafe.article.domain

import com.widehouse.cafe.article.Article
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ArticleTest : StringSpec({
    "Article should be created" {
        val article = Article("id", "boardId", "title", "body", LocalDateTime.now())

        article.id shouldBe "id"
        article.boardId shouldBe "boardId"
        article.title shouldBe "title"
        article.body shouldBe "body"
    }
})
