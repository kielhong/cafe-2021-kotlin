package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Article
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class ArticleRepositoryTest @Autowired constructor(
    private val articleRepository: ArticleRepository
) {
    lateinit var boardId: String

    @BeforeEach
    internal fun setUp() {
        boardId = "board"
    }

    @Test
    fun `testFindAllByBoardId`() {
        // given
        val article1 = articleRepository.save(Article("1", boardId)).block()
        val article2 = articleRepository.save(Article("2", boardId)).block()
        // when
        val result = articleRepository.findAllByBoardId(boardId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }
}
