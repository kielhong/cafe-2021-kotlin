package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Article
import com.widehouse.cafe.repository.ArticleRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ArticleService(
    private val articleRepository: ArticleRepository
) {
    fun getArticle(articleId: String): Mono<Article> {
        return articleRepository.findById(articleId)
    }
}
