package com.widehouse.cafe.repository

import com.widehouse.cafe.model.Board
import com.widehouse.cafe.cafe.model.Cafe
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
        cafe = Cafe("test")
    }

    @Test
    fun when_findByCafeUrl_then_returnFluxBoard() {
        // given
        val board1 = boardRepository.save(Board("1", "cafeUrl")).block()
        val board2 = boardRepository.save(Board("2", "cafeUrl")).block()
        // when
        val result = boardRepository.findByCafeUrl("cafeUrl")
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it).isEqualTo(board1) }
            .assertNext { then(it).isEqualTo(board2) }
            .expectComplete()
            .verify()
    }
}
