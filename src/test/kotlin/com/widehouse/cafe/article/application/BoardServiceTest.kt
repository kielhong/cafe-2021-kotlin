package com.widehouse.cafe.article.application

import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.adapter.out.persistence.BoardRepository
import com.widehouse.cafe.cafe.CafeFixtures
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class BoardServiceTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val boardRepository = mockk<BoardRepository>()
    val service = BoardService(boardRepository)

    val cafe = CafeFixtures.create()

    describe("getBoard") {
        val board = BoardFixtures.create("1234", cafe.id)
        context("boardRepository find board by id") {
            every { boardRepository.findById(board.id) } returns Mono.just(board)
            val result = service.getBoard(board.id)
            it("should return board") {
                StepVerifier.create(result)
                    .assertNext { it shouldBe board }
                    .verifyComplete()
            }
        }
    }

    describe("listBoard") {
        val board1 = BoardFixtures.create("1", cafe.id, 2)
        val board2 = BoardFixtures.create("2", cafe.id, 1)

        context("boardRepository findByCafeId") {
            every { boardRepository.findByCafeId(cafe.id) } returns Flux.just(board1, board2)

            val result = service.listBoard(cafe.id)
            it("should list boards order by listOrder") {
                StepVerifier.create(result)
                    .assertNext { it shouldBe board2 }
                    .assertNext { it shouldBe board1 }
                    .verifyComplete()
                verify { boardRepository.findByCafeId(cafe.id) }
            }
        }
    }
})
