package com.widehouse.cafe.article.application

import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.adapter.`in`.web.dto.BoardRequest
import com.widehouse.cafe.article.adapter.out.persistence.BoardRepository
import com.widehouse.cafe.article.domain.BoardType
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
import java.util.UUID

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
                verify { boardRepository.findById(board.id) }
            }
        }
    }

    describe("listBoard") {
        val board1 = BoardFixtures.create("1", cafe.id, "board1", BoardType.Board, 2)
        val board2 = BoardFixtures.create("2", cafe.id, "board2", BoardType.Board, 1)

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

    describe("create Board") {
        it("repository create and return board") {
            val request = BoardRequest(cafe.id, "board", BoardType.Board, 1)
            val board = BoardFixtures.create(UUID.randomUUID().toString(), request.cafeId, request.name, request.boardType, request.listOrder)
            every { boardRepository.save(any()) } returns Mono.just(board)

            val result = service.createBoard(request)

            StepVerifier.create(result)
                .assertNext { it shouldBe board }
                .verifyComplete()

            verify {
                boardRepository.save(
                    withArg {
                        it.cafeId shouldBe request.cafeId
                        it.name shouldBe request.name
                        it.listOrder shouldBe request.listOrder
                    }
                )
            }
        }
    }
})
