package com.widehouse.cafe.cafe.application.port.`in`

import com.widehouse.cafe.cafe.adapter.`in`.web.CafeRequest
import com.widehouse.cafe.cafe.domain.Cafe
import reactor.core.publisher.Mono

interface CafeCommandUseCase {
    fun create(cafe: Cafe): Mono<Cafe>
    fun remove(id: String): Mono<Void>
    fun update(id: String, cafeRequest: CafeRequest): Mono<Cafe>
}
