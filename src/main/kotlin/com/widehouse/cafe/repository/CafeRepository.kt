package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Cafe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface CafeRepository : ReactiveMongoRepository<Cafe, String> {
    fun findByUrl(url: String): Mono<Cafe>
}
