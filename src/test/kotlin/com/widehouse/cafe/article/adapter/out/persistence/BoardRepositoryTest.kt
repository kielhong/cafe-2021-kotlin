package com.widehouse.cafe.article.adapter.out.persistence

import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.domain.Cafe
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class BoardRepositoryTest @Autowired constructor(
    private val boardRepository: BoardRepository
) {
    lateinit var cafe: Cafe

    @BeforeEach
    internal fun setUp() {
        cafe = CafeFixtures.create()
    }

    @Test
    fun when_findByCafeId_then_returnFluxBoard() {
        // given
        val board1 = boardRepository.save(BoardFixtures.create("1", cafe.id)).block()!!
        val board2 = boardRepository.save(BoardFixtures.create("2", cafe.id)).block()!!
        // when
        val result = boardRepository.findByCafeId(cafe.id)
        // then
        StepVerifier.create(result)
            .assertNext { it.id shouldBe board1.id }
            .assertNext { it.id shouldBe board2.id }
            .verifyComplete()
    }
}
