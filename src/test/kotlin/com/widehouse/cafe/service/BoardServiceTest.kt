package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Board
import com.widehouse.cafe.domain.Cafe
import com.widehouse.cafe.repository.BoardRepository
import com.widehouse.cafe.repository.CafeRepository
import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
class BoardServiceTest {
    lateinit var service: BoardService
    @Mock
    lateinit var cafeRepository: CafeRepository
    @Mock
    lateinit var boardRepository: BoardRepository

    @BeforeEach
    internal fun setUp() {
        service = BoardService(cafeRepository, boardRepository)
    }

    @Test
    fun given_cafeUrl_boardId_when_getBoard_then_returnBoard() {
        // given
        val cafeUrl = "test"
        val cafeId = "12"
        val boardId = "1234"
        val cafe = Cafe(cafeId, cafeUrl)
        given(cafeRepository.findByUrl(cafeUrl)).willReturn(Mono.just(cafe))
        val board = Board(boardId, cafe.id!!)
        given(boardRepository.findByCafeIdAndId(cafe.id!!, boardId)).willReturn(Mono.just(board))

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
