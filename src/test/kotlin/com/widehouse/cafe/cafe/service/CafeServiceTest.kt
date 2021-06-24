package com.widehouse.cafe.cafe.service

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import com.widehouse.cafe.cafe.repository.CafeRepository
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class CafeServiceTest {
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
        given(cafeRepository.findById("url")).willReturn(Mono.just(cafe))
        // when
        val result = service.getCafe("url")
        // then
        StepVerifier
            .create(result)
            .assertNext { c -> then(c).isEqualTo(cafe) }
            .expectComplete()
            .verify()
    }

    @Nested
    inner class CreateCafe {
        @Test
        fun given_cafe_when_create_then_SaveCafe() {
            // given
            val cafe = CafeFixtures.create()
            given(cafeRepository.save(cafe)).willReturn(Mono.just(cafe))
            // when
            val result = service.create(cafe)
            // then
            StepVerifier.create(result)
                .assertNext { then(it).isEqualTo(cafe) }
                .verifyComplete()
        }
    }
}
