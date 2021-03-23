package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Cafe
import com.widehouse.cafe.repository.CafeRepository
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
        val cafe = Cafe("id","test")
        given(cafeRepository.findById("test"))
            .willReturn(Mono.just(cafe))
        // when
        val result = service.getCafe("test")
        // then
        StepVerifier
            .create(result)
            .assertNext { c ->
                then(c.url).isEqualTo("test")
            }
            .expectComplete()
            .verify()
    }
}