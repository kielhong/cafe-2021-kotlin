package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Board
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
class BoardRepositoryTest @Autowired constructor(
    private val boardRepository: BoardRepository) {

    @Test
    fun when_findByCafeIdAndId_then_returnMonoBoard() {
        // given
        val board = boardRepository.save(Board("boardId", "cafeUrl")).block()
        // when
        val result = boardRepository.findByCafeUrlAndId("cafeUrl", "boardId")
        // then
        StepVerifier
            .create(result)
            .assertNext { b -> then(b).isEqualTo(board) }
            .expectComplete()
            .verify()
    }
}