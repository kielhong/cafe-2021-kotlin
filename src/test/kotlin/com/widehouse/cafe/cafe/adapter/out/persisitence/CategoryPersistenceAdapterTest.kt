package com.widehouse.cafe.cafe.adapter.out.persisitence

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
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
            val category1 = CategoryEntity(1L, "name1", 1)
            val category2 = CategoryEntity(2L, "name2", 2)
            every { categoryMongoRepository.findAll() } returns Flux.just(category1, category2)

            it("should list all categories") {
                adapter.loadAllCategory()
                    .`as`(StepVerifier::create)
                    .assertNext {
                        it.id shouldBe category1.id
                        it.name shouldBe category1.name
                        it.listOrder shouldBe category1.listOrder
                    }
                    .expectNextCount(1)
                    .verifyComplete()

                verify { categoryMongoRepository.findAll() }
            }
        }
    }
}
