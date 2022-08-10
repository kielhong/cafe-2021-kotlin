package com.widehouse.cafe.cafe.application

import com.widehouse.cafe.cafe.CafeFixtures
import com.widehouse.cafe.cafe.adapter.`in`.web.CafeRequest
import com.widehouse.cafe.cafe.application.port.out.CafeRepository
import com.widehouse.cafe.common.exception.AlreadyExistException
import com.widehouse.cafe.common.exception.DataNotFoundException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.dao.DuplicateKeyException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class CafeServiceTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val cafeRepository = mockk<CafeRepository>()
    val service = CafeService(cafeRepository)

    describe("getCafe") {
        context("cafeRepository load cafe of id") {
            val cafe = CafeFixtures.create("test")
            every { cafeRepository.loadCafe(any()) } returns Mono.just(cafe)

            it("should be cafe of id") {
                service.getCafe("test")
                    .`as`(StepVerifier::create)
                    .assertNext { it shouldBe cafe }
                    .verifyComplete()

                verify { cafeRepository.loadCafe(any()) }
            }
        }
    }

    describe("Cafe list by theme") {
        context("cafeRepository listByTheme listFluxCafe") {
            val categoryId = 1L
            val cafe1 = CafeFixtures.create("1", "name1", "desc1", categoryId)
            val cafe2 = CafeFixtures.create("2", "name2", "desc2", categoryId)
            every { cafeRepository.loadCafeByCategory(any()) } returns Flux.just(cafe1, cafe2)
            it("should be flux of cafes") {
                service.listByCategory(categoryId)
                    .`as`(StepVerifier::create)
                    .assertNext { it shouldBe cafe1 }
                    .assertNext { it shouldBe cafe2 }
                    .verifyComplete()
            }
        }
    }

    describe("create Cafe") {
        val cafe = CafeFixtures.create("1")
        context("cafeRepository create and save cafe") {
            every { cafeRepository.createCafe(any()) } returns Mono.just(cafe)
            it("should be cafe of id") {
                service.create(cafe)
                    .`as`(StepVerifier::create)
                    .assertNext { it shouldBe cafe }
                    .verifyComplete()
                verify { cafeRepository.createCafe(any()) }
            }
        }

        context("cafeRepository throwDuplicateKeyException") {
            every { cafeRepository.createCafe(cafe) } returns Mono.error(DuplicateKeyException(""))
            it("should throws AlreadyExistException") {
                service.create(cafe)
                    .`as`(StepVerifier::create)
                    .expectError(AlreadyExistException::class.java)
                    .verify()
                verify { cafeRepository.createCafe(any()) }
            }
        }
    }

    describe("Update Cafe") {
        val cafeId = "test"
        val cafeRequest = CafeRequest(id = cafeId, name = "newName", description = "newDesc", categoryId = 2L)
        val updatedCafe = CafeFixtures.create(id = cafeId, name = cafeRequest.name, description = cafeRequest.description, categoryId = cafeRequest.categoryId)
        every { cafeRepository.updateCafe(any()) } returns Mono.just(updatedCafe)

        context("cafe exists") {
            every { cafeRepository.loadCafe(cafeId) } returns Mono.just(CafeFixtures.create(cafeId))

            val result = service.update(cafeId, cafeRequest)

            it("should update cafe") {
                StepVerifier.create(result)
                    .assertNext { it.id shouldBe cafeId }
                    .verifyComplete()

                verify {
                    cafeRepository.loadCafe(cafeId)
                    cafeRepository.updateCafe(
                        withArg {
                            it.id shouldBe cafeId
                            it.name shouldBe cafeRequest.name
                            it.description shouldBe cafeRequest.description
                            it.categoryId shouldBe cafeRequest.categoryId
                        }
                    )
                }
            }
        }

        context("cafe not exists") {
            every { cafeRepository.loadCafe(cafeId) } returns Mono.empty()
            val result = service.update(cafeId, cafeRequest)

            it("should throws DomainNotFoundException") {
                StepVerifier.create(result)
                    .expectError(DataNotFoundException::class.java)

                verify(exactly = 0) { cafeRepository.updateCafe(any()) }
            }
        }
    }

    describe("Remove Cafe") {
        val cafeId = "test"
        context("cafeRepository delete cafe") {
            every { cafeRepository.deleteCafe(cafeId) } returns Mono.empty()
            service.remove(cafeId)

            it("should delete cafe") {
                verify { cafeRepository.deleteCafe(cafeId) }
            }
        }
    }
})
