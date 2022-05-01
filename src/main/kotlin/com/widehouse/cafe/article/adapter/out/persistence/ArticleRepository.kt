package com.widehouse.cafe.article.adapter.out.persistence

import com.widehouse.cafe.article.Article
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ArticleRepository : ReactiveMongoRepository<Article, String> {
    fun findByBoards(boardId: String): Flux<Article>

    fun findByBoardsIn(boardIds: List<String>): Flux<Article>
}
