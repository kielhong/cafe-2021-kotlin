package com.widehouse.cafe.article.service

import com.widehouse.cafe.article.model.Article
import com.widehouse.cafe.article.repository.ArticleRepository
import com.widehouse.cafe.article.repository.BoardRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.Collections

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val boardRepository: BoardRepository
) {
    fun getArticle(articleId: String) = articleRepository.findById(articleId)

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

    fun create(article: Article): Mono<Article> = articleRepository.save(article)
}
