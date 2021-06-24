package com.widehouse.cafe.cafe.repository

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import org.assertj.core.api.BDDAssertions.then
import org.assertj.core.api.BDDAssertions.thenThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
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

    @Test
    fun given_duplicated_cafe_when_insert_throw_Exception() {
        // given
        val cafe = CafeFixtures.create()
        // when
        cafeRepository.insert(cafe).block()
        // then
        thenThrownBy { cafeRepository.insert(cafe).block() }
            .isInstanceOf(DuplicateKeyException::class.java)
    }
}
