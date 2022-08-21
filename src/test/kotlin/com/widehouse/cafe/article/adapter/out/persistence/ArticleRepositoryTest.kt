package com.widehouse.cafe.article.adapter.out.persistence

import com.widehouse.cafe.article.Article
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier
import java.time.LocalDateTime

@DataMongoTest
internal class ArticleRepositoryTest @Autowired constructor(
    private val articleRepository: ArticleRepository
) {
    @Test
    fun testFindByBoards() {
        // given
        val boardId = "boardId"
        val article1 = articleRepository.save(Article("1", boardId, "title1", "body1", LocalDateTime.now())).block()!!
        val article2 = articleRepository.save(Article("2", boardId, "title2", "body2", LocalDateTime.now())).block()!!
        // when
        val result = articleRepository.findByBoardId(boardId)
        // then
        StepVerifier.create(result)
            .assertNext { it.id shouldBe article1.id }
            .assertNext { it.id shouldBe article2.id }
            .verifyComplete()
    }

    @Test
    fun testFindBoardsIn() {
        // given
        val article1 = articleRepository.save(Article("1", "boardId1", "title1", "body1", LocalDateTime.now())).block()!!
        val article2 = articleRepository.save(Article("2", "boardId2", "title1", "body1", LocalDateTime.now())).block()!!
        // when
        val result = articleRepository.findByBoardIdIn(listOf("boardId1", "boardId2"))
        // then
        StepVerifier.create(result)
            .assertNext { it.id shouldBe article1.id }
            .assertNext { it.id shouldBe article2.id }
            .verifyComplete()
    }
}
