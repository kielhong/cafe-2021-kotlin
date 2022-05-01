package com.widehouse.cafe.cafe.adapter.out.persisitence

import com.widehouse.cafe.cafe.domain.Cafe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CafeRepository : ReactiveMongoRepository<Cafe, String> {
    fun findByTheme(theme: String): Flux<Cafe>
}
