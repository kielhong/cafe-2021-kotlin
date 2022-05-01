package com.widehouse.cafe.cafe.application.port.`in`

import com.widehouse.cafe.cafe.domain.Cafe
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CafeQueryUseCase {
    fun getCafe(id: String): Mono<Cafe>
    fun listByTheme(theme: String): Flux<Cafe>
}
