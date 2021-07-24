package com.widehouse.cafe.cafe.repository

import com.widehouse.cafe.cafe.model.Cafe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CafeRepository : ReactiveMongoRepository<Cafe, String> {
    fun findByTheme(theme: String): Flux<Cafe>
}
