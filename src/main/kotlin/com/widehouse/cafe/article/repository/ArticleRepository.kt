package com.widehouse.cafe.article.repository

import com.widehouse.cafe.article.model.Article
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ArticleRepository : ReactiveMongoRepository<Article, String> {
    fun findByBoardId(boardId: String): Flux<Article>

    fun findByBoardIdIn(boardIds: List<String>): Flux<Article>
}
