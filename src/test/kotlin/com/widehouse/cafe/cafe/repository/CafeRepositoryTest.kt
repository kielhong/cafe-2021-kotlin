package com.widehouse.cafe.cafe.repository

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.model.Cafe
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.test.StepVerifier

@DataMongoTest
internal class CafeRepositoryTest @Autowired constructor(
    private val template: ReactiveMongoTemplate,
    private val cafeRepository: CafeRepository
) {
    @AfterEach
    internal fun tearDown() {
        template.dropCollection(Cafe::class.java).subscribe()
    }

    @Test
    fun when_findByUrl_then_returnMonoCafe() {
        // given
        val cafe = template.save(Cafe("test", "name", "desc")).block()
        // when
        val result = cafeRepository.findById(cafe!!.id)
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it).isEqualTo(cafe) }
            .verifyComplete()
    }

    @Test
    fun given_duplicated_cafe_when_insert_throw_Exception() {
        // given
        val cafe = CafeFixtures.create()
        template.save(cafe).block()
        // when
        val result = cafeRepository.insert(cafe)
        // then
        StepVerifier.create(result)
            .expectError(DuplicateKeyException::class.java)
            .verify()
    }
}
