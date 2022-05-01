package com.widehouse.cafe.article.domain

import com.widehouse.cafe.article.Article
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class ArticleTest : StringSpec({
    "Article should be created" {
        val article = Article("id", listOf("board1Id", "board2Id"), "title", "body")
        article.id shouldBe "id"
        article.boards.shouldContainAll("board1Id", "board2Id")
        article.title shouldBe "title"
        article.body shouldBe "body"
    }
})
