package com.widehouse.cafe.cafe.service

import com.widehouse.cafe.cafe.model.Cafe
import com.widehouse.cafe.cafe.repository.CafeRepository
import com.widehouse.cafe.common.exception.AlreadyExistException
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CafeService(private val cafeRepository: CafeRepository) {
    @Transactional(readOnly = true)
    fun getCafe(id: String): Mono<Cafe> = cafeRepository.findById(id)

    @Transactional(readOnly = true)
    fun listByTheme(theme: String): Flux<Cafe> = cafeRepository.findByTheme(theme)

    @Transactional
    fun create(cafe: Cafe): Mono<Cafe> = cafeRepository.insert(cafe)
        .onErrorMap(DuplicateKeyException::class.java) { AlreadyExistException(cafe.id) }
}
