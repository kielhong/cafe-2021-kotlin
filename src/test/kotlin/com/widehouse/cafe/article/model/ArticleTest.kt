package com.widehouse.cafe.article.model

import com.widehouse.cafe.article.Article
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ArticleTest: StringSpec( {
    "Article should be created" {
        val article = Article("id", "boardId", "title", "body")
        article.id shouldBe "id"
    }
})