package com.widehouse.cafe.cafe.application.port.out

import com.widehouse.cafe.cafe.domain.Cafe
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CafeRepository {
    fun loadCafe(id: String): Mono<Cafe>
    fun loadCafeByCategory(categoryId: Long): Flux<Cafe>
    fun createCafe(cafe: Cafe): Mono<Cafe>
}
