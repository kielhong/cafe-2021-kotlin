package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface BoardRepository : ReactiveMongoRepository<Board, String> {
    fun findByCafeIdAndId(cafeId: String, id: String): Mono<Board>
}