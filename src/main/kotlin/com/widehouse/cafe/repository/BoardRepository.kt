package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface BoardRepository : ReactiveMongoRepository<Board, String> {
    fun findByCafeUrl(cafeUrl: String): Flux<Board>
}
