package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Article
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ArticleRepository : ReactiveMongoRepository<Article, String> {
    fun findAllByBoardId(boardId: String): Flux<Article>
}
