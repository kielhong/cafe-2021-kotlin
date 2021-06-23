package com.widehouse.cafe.service

import com.widehouse.cafe.model.Article
import com.widehouse.cafe.repository.ArticleRepository
import com.widehouse.cafe.repository.BoardRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.Collections

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val boardRepository: BoardRepository
) {
    fun getArticle(articleId: String) = articleRepository.findById(articleId)

    fun listArticleByBoard(boardId: String): Flux<Article> = articleRepository.findByBoardId(boardId)

    fun listArticleByCafe(cafeUrl: String): Flux<Article> {
        val boardIds = boardRepository.findByCafeUrl(cafeUrl)
            .map { it.id }
            .collectList()
            .blockOptional()
            .orElse(Collections.emptyList())
            .toList()

        return articleRepository.findByBoardIdIn(boardIds)
    }
}
