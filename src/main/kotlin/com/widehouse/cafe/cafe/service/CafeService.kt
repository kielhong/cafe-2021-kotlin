package com.widehouse.cafe.cafe.service

import com.widehouse.cafe.cafe.model.Cafe
import com.widehouse.cafe.cafe.repository.CafeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class CafeService(private val cafeRepository: CafeRepository) {
    @Transactional(readOnly = true)
    fun getCafe(id: String): Mono<Cafe> = cafeRepository.findById(id)

    @Transactional
    fun create(cafe: Cafe): Mono<Cafe> {
        return cafeRepository.save(cafe)
    }
}
