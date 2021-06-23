package com.widehouse.cafe.repository

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import com.widehouse.cafe.model.Board
import org.assertj.core.api.BDDAssertions.then
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
    fun when_findByCafeUrl_then_returnFluxBoard() {
        // given
        val board1 = boardRepository.save(Board("1", cafe.id)).block()
        val board2 = boardRepository.save(Board("2", cafe.id)).block()
        // when
        val result = boardRepository.findByCafeId(cafe.id)
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it).isEqualTo(board1) }
            .assertNext { then(it).isEqualTo(board2) }
            .expectComplete()
            .verify()
    }
}
