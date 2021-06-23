package com.widehouse.cafe.cafe.repository

import com.widehouse.cafe.cafe.model.Cafe
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest
internal class CafeRepositoryTest @Autowired constructor(
    private val cafeRepository: CafeRepository
) {

    @Test
    fun when_findByUrl_then_returnMonoCafe() {
        // given
        val cafe = cafeRepository.save(Cafe("url")).block()
        // when
        val result = cafeRepository.findById("url")
        // then
        StepVerifier
            .create(result)
            .assertNext { c -> then(c).isEqualTo(cafe) }
            .expectComplete()
            .verify()
    }
}
