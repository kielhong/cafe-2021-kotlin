package com.widehouse.cafe.cafe.application

import com.widehouse.cafe.cafe.CategoryFixtures
import com.widehouse.cafe.cafe.application.port.out.CategoryRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

internal class CategoryServiceTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
}) {
    lateinit var service: CategoryService

    @MockK
    lateinit var categoryRepository: CategoryRepository

    init {
        beforeTest {
            MockKAnnotations.init(this)

            service = CategoryService(categoryRepository)
        }

        describe("listCategories") {
            val categories = (1..3).map { CategoryFixtures.create(id = it.toLong(), listOrder = 4 - it) }
            every { categoryRepository.loadAllCategory() } returns Flux.fromIterable(categories)

            val result = service.listCategories()

            it("should list all categories") {
                StepVerifier.create(result)
                    .expectNextCount(3)
                    .verifyComplete()

                verify { categoryRepository.loadAllCategory() }
            }

            it("should list sorted by listOrder") {
                StepVerifier.create(result)
                    .expectNextMatches { it.listOrder == 1 }
                    .expectNextMatches { it.listOrder == 2 }
                    .expectNextMatches { it.listOrder == 3 }
                    .verifyComplete()
            }
        }
    }
}
