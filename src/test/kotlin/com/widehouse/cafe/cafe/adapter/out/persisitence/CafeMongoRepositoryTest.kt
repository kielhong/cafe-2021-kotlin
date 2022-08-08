package com.widehouse.cafe.cafe.adapter.out.persisitence

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.domain.Cafe
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.test.StepVerifier

@DataMongoTest
internal class CafeMongoRepositoryTest @Autowired constructor(
    private val template: ReactiveMongoTemplate,
    private val cafeMongoRepository: CafeMongoRepository
) {
    @AfterEach
    internal fun tearDown() {
        template.dropCollection(Cafe::class.java).subscribe()
    }

    @Test
    fun when_findById_then_returnMonoCafe() {
        // given
        val cafe = template.save(Cafe("test", "name", "desc", 1L)).block()
        // when
        val result = cafeMongoRepository.findById(cafe!!.id)
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
        val result = cafeMongoRepository.insert(cafe)
        // then
        StepVerifier.create(result)
            .expectError(DuplicateKeyException::class.java)
            .verify()
    }

    @Test
    fun given_categoryId_when_listByCategory_then_listFlux() {
        // given
        val categoryId = 1L
        template.save(CafeFixtures.create("1", "name1", "desc1", categoryId)).block()
        template.save(CafeFixtures.create("2", "name2", "desc2", categoryId)).block()
        // when
        val result = cafeMongoRepository.findByCategoryId(categoryId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it.categoryId).isEqualTo(categoryId) }
            .assertNext { then(it.categoryId).isEqualTo(categoryId) }
            .verifyComplete()
    }
}
