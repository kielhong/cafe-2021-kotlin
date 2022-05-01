package com.widehouse.cafe.article.adapter.out.persistence

import com.widehouse.cafe.article.domain.Board
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface BoardRepository : ReactiveMongoRepository<Board, String> {
    fun findByCafeId(cafeId: String): Flux<Board>
}
