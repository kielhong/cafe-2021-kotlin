package com.widehouse.cafe.article.repository

import com.widehouse.cafe.article.model.Article
import com.widehouse.cafe.article.repository.ArticleRepository
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
    fun testFindByBoardId() {
        // given
        val boardId = "boardId"
        val article1 = articleRepository.save(Article("1", boardId)).block()
        val article2 = articleRepository.save(Article("2", boardId)).block()
        // when
        val result = articleRepository.findByBoardId(boardId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }

    @Test
    fun testFindBoardIdIn() {
        // given
        val article1 = articleRepository.save(Article("1", "boardId1")).block()
        val article2 = articleRepository.save(Article("2", "boardId2")).block()
        // when
        val result = articleRepository.findByBoardIdIn(listOf("boardId1", "boardId2"))
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }
}
