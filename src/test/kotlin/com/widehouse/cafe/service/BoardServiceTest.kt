package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.domain.Cafe
import com.widehouse.cafe.repository.BoardRepository
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
        cafe = Cafe("test")
    }

    @Nested
    inner class GetBoard {
        @Test
        fun given_cafeUrl_boardId_when_getBoard_then_returnBoard() {
            // given
            val board = Board("1234", cafe.url)
            given(boardRepository.findById(board.id)).willReturn(Mono.just(board))
            // when
            val result = service.getBoard(cafe.url, board.id)
            // then
            StepVerifier
                .create(result)
                .assertNext { then(it).isEqualTo(board) }
                .expectComplete()
                .verify()
        }

        @Test
        fun given_otherCafe_when_getBoard_thenMonoEmpty() {
            // given
            val board = Board("1234", "otherurl")
            given(boardRepository.findById(board.id)).willReturn(Mono.just(board))
            // when
            val result = service.getBoard(cafe.url, board.id)
            // then
            StepVerifier
                .create(result)
                .verifyComplete()
        }
    }

    @Test
    fun given_cafeUrl_when_listBoard_then_listBoardByCafe() {
        // given
        val board1 = Board("1", cafe.url)
        val board2 = Board("2", cafe.url)
        given(boardRepository.findByCafeUrl(cafe.url))
            .willReturn(Flux.just(board1, board2))
        // when
        val result = service.listBoard(cafe.url)
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it).isEqualTo(board1) }
            .assertNext { then(it).isEqualTo(board2) }
            .expectComplete()
            .verify()
    }
}
