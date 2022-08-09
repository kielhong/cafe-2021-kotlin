package com.widehouse.cafe.cafe.application

import com.widehouse.cafe.cafe.application.port.`in`.CafeCreateUseCase
import com.widehouse.cafe.cafe.application.port.`in`.CafeQueryUseCase
import com.widehouse.cafe.cafe.application.port.out.CafeRepository
import com.widehouse.cafe.cafe.domain.Cafe
import com.widehouse.cafe.common.exception.AlreadyExistException
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CafeService(
    private val cafeRepository: CafeRepository
) : CafeQueryUseCase, CafeCreateUseCase {
    @Transactional(readOnly = true)
    override fun getCafe(id: String): Mono<Cafe> =
        cafeRepository.loadCafe(id)

    @Transactional(readOnly = true)
    override fun listByCategory(categoryId: Long): Flux<Cafe> =
        cafeRepository.loadCafeByCategory(categoryId)

    @Transactional
    override fun create(cafe: Cafe): Mono<Cafe> =
        cafeRepository.createCafe(cafe)
            .onErrorMap(DuplicateKeyException::class.java) { AlreadyExistException(cafe.id) }

    @Transactional
    override fun remove(id: String): Mono<Void> =
        cafeRepository.deleteCafe(id)
}
