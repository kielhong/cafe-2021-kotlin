package com.widehouse.cafe.article.repository

import com.widehouse.cafe.article.model.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface BoardRepository : ReactiveMongoRepository<Board, String> {
    fun findByCafeId(cafeId: String): Flux<Board>
}
