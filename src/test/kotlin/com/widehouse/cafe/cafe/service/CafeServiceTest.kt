package com.widehouse.cafe.cafe.service

import com.widehouse.cafe.cafe.repository.CafeRepository
import com.widehouse.cafe.cafe.model.Cafe
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
class CafeServiceTest {
    lateinit var service: CafeService
    @Mock
    lateinit var cafeRepository: CafeRepository

    @BeforeEach
    internal fun setUp() {
        service = CafeService(cafeRepository)
    }

    @Test
    fun given_repository_when_getCafe_then_returnMonoCafe() {
        // given
        val cafe = Cafe("url")
        given(cafeRepository.findByUrl("url"))
            .willReturn(Mono.just(cafe))
        // when
        val result = service.getCafe("url")
        // then
        StepVerifier
            .create(result)
            .assertNext { c -> then(c).isEqualTo(cafe) }
            .expectComplete()
            .verify()
    }
}
