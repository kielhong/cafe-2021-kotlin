package com.widehouse.cafe.cafe.application.port.`in`

import com.widehouse.cafe.cafe.domain.Cafe
import reactor.core.publisher.Mono

interface CafeCreateUseCase {
    fun create(cafe: Cafe): Mono<Cafe>
}
