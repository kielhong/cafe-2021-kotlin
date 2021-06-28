package com.widehouse.cafe.article.service

import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.repository.BoardRepository
import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class BoardServiceTest {
    lateinit var service: BoardService
    @Mock
    lateinit var boardRepository: BoardRepository

    lateinit var cafe: Cafe

    @BeforeEach
    internal fun setUp() {
        service = BoardService(boardRepository)
        cafe = CafeFixtures.create()
    }

    @Nested
    inner class GetBoard {
        @Test
        fun given_cafeUrl_boardId_when_getBoard_then_returnBoard() {
            // given
            val board = Board("1234", cafe.id)
            given(boardRepository.findById(board.id)).willReturn(Mono.just(board))
            // when
            val result = service.getBoard(board.id)
            // then
            StepVerifier.create(result)
                .assertNext { then(it).isEqualTo(board) }
                .verifyComplete()
        }
    }

    @Test
    fun given_cafeUrl_when_listBoard_then_listBoardByCafe() {
        // given
        val board1 = Board("1", cafe.id)
        val board2 = Board("2", cafe.id)
        given(boardRepository.findByCafeId(cafe.id))
            .willReturn(Flux.just(board1, board2))
        // when
        val result = service.listBoard(cafe.id)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(board1) }
            .assertNext { then(it).isEqualTo(board2) }
            .verifyComplete()
    }
}
