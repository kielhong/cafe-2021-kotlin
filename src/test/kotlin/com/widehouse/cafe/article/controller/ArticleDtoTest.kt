package com.widehouse.cafe.article.controller

import com.widehouse.cafe.article.controller.dto.ArticleDto
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll

class ArticleDtoTest : StringSpec({
    "ArticleDto should have multiple boardId" {
        val dto = ArticleDto(boards = listOf("board1Id", "board2Id"), title = "title", body = "body")
        dto.boards.shouldContainAll("board1Id", "board2Id")
    }
})
