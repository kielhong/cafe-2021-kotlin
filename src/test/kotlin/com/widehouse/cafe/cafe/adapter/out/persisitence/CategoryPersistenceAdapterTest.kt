package com.widehouse.cafe.cafe.adapter.out.persisitence

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class CategoryPersistenceAdapterTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    lateinit var adapter: CategoryPersistenceAdapter

    private val categoryMongoRepository = mockk<CategoryMongoRepository>()

    init {
        beforeTest {
            adapter = CategoryPersistenceAdapter(categoryMongoRepository)
        }

        describe("loadAllCategory") {
            val categories = (1..2).map { CategoryEntity(it.toLong(), "name$it", it) }
            every { categoryMongoRepository.findAll() } returns Flux.fromIterable(categories)

            it("should list all categories") {
                adapter.loadAllCategory()
                    .`as`(StepVerifier::create)
                    .expectNextMatches { it.id == 1L && it.name == "name1" }
                    .expectNextCount(1)
                    .verifyComplete()

                verify { categoryMongoRepository.findAll() }
            }
        }
    }
}
