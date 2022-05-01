package com.widehouse.cafe.article.adapter.out.persistence

import com.widehouse.cafe.article.Article
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
internal class ArticleRepositoryTest @Autowired constructor(
    private val articleRepository: ArticleRepository
) {
    @Test
    fun testFindByBoards() {
        // given
        val boardId = "boardId"
        val article1 = articleRepository.save(Article("1", listOf(boardId), "title1", "body1")).block()
        val article2 = articleRepository.save(Article("2", listOf(boardId), "title2", "body2")).block()
        // when
        val result = articleRepository.findByBoards(boardId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }

    @Test
    fun testFindBoardsIn() {
        // given
        val article1 = articleRepository.save(Article("1", listOf("boardId1"), "title1", "body1")).block()
        val article2 = articleRepository.save(Article("2", listOf("boardId2"), "title1", "body1")).block()
        // when
        val result = articleRepository.findByBoardsIn(listOf("boardId1", "boardId2"))
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }
}
