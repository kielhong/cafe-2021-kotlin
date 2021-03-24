package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.repository.BoardRepository
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class BoardServiceTest {
    lateinit var service: BoardService
    @Mock
    lateinit var boardRepository: BoardRepository

    @BeforeEach
    internal fun setUp() {
        service = BoardService(boardRepository)
    }

    @Test
    fun given_cafeUrl_boardId_when_getBoard_then_returnBoard() {
        // given
        val cafeUrl = "test"
        val boardId = "1234"
        val board = Board(boardId, cafeUrl)
        given(boardRepository.findByCafeUrlAndId(cafeUrl, boardId)).willReturn(Mono.just(board))

        // when
        val result = service.getBoard(cafeUrl, boardId)
        // then
        StepVerifier
            .create(result)
            .assertNext { b -> then(b).isEqualTo(board) }
            .expectComplete()
            .verify()
    }
}
