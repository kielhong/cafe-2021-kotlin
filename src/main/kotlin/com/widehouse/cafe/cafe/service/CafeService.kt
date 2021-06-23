package com.widehouse.cafe.cafe.service

import com.widehouse.cafe.cafe.model.Cafe
import com.widehouse.cafe.cafe.repository.CafeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class CafeService(private val cafeRepository: CafeRepository) {
    @Transactional(readOnly = true)
    fun getCafe(url: String): Mono<Cafe> {
        return cafeRepository.findById(url)
    }
}
