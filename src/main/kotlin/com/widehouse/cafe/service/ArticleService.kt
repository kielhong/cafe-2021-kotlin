package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Article
import com.widehouse.cafe.repository.ArticleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    fun getArticle(articleId: String) = articleRepository.findById(articleId)

    fun listArticleByBoard(boardId: String): Flux<Article> = articleRepository.findAllByBoardId(boardId)
}
