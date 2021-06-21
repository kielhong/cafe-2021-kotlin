package com.widehouse.cafe.controller

import com.widehouse.cafe.domain.Article
import com.widehouse.cafe.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
class ArticleController(private val articleService: ArticleService) {
    @GetMapping("article/{articleId}")
    fun getArticle(@PathVariable articleId: String) = articleService.getArticle(articleId)
        .switchIfEmpty { Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND)) }

    @GetMapping("article", params = ["boardId"])
    fun listArticleByBoard(@RequestParam boardId: String): Flux<Article> = articleService.listArticleByBoard(boardId)

    @GetMapping("article", params = ["cafeUrl"])
    fun listArticleByCafe(@RequestParam cafeUrl: String): Flux<Article> = articleService.listArticleByCafe(cafeUrl)
}
