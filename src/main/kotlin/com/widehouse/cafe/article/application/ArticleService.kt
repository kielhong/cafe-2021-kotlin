package com.widehouse.cafe.article.application

import com.widehouse.cafe.article.Article
import com.widehouse.cafe.article.adapter.`in`.web.dto.ArticleRequest
import com.widehouse.cafe.article.adapter.out.persistence.ArticleRepository
import com.widehouse.cafe.article.adapter.out.persistence.BoardRepository
import com.widehouse.cafe.common.exception.DataNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Collections
import java.util.UUID

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val boardRepository: BoardRepository
) {
    fun getArticle(articleId: String): Mono<Article> = articleRepository.findById(articleId)

    fun listByBoard(boardId: String): Flux<Article> = articleRepository.findByBoardId(boardId)

    fun listByCafe(cafeId: String): Flux<Article> {
        val boardIds = boardRepository.findByCafeId(cafeId)
            .map { it.id }
            .collectList()
            .blockOptional()
            .orElse(Collections.emptyList())
            .toList()

        return articleRepository.findByBoardIdIn(boardIds)
    }

    fun create(articleRequest: ArticleRequest): Mono<Article> =
        articleRepository.save(Article(UUID.randomUUID().toString(), articleRequest.boardId, articleRequest.title, articleRequest.body))

    fun update(articleId: String, articleRequest: ArticleRequest): Mono<Article> =
        articleRepository.findById(articleId)
            .flatMap { article ->
                articleRepository.save(Article(article.id, articleRequest.boardId, articleRequest.title, articleRequest.body))
            }
            .switchIfEmpty(Mono.error(DataNotFoundException("Article(id=$articleId) not found")))
}
