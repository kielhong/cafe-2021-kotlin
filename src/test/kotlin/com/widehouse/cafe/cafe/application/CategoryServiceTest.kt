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
            val categories = (1L..5L).map { CategoryFixtures.create(it) }
            every { categoryRepository.loadAllCategory() } returns Flux.fromIterable(categories)

            it("should list all categories") {
                service.listCategories()
                    .`as`(StepVerifier::create)
                    .expectNextCount(5)
                    .verifyComplete()

                verify { categoryRepository.loadAllCategory() }
            }
        }
    }
}
