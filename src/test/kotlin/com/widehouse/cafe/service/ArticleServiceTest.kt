package com.widehouse.cafe.service

import com.widehouse.cafe.domain.Article
import com.widehouse.cafe.repository.ArticleRepository
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class ArticleServiceTest {
    lateinit var service: ArticleService
    @Mock
    lateinit var articleRepository: ArticleRepository

    lateinit var article: Article

    @BeforeEach
    internal fun setUp() {
        service = ArticleService(articleRepository)
        article = Article("1234", "board")
    }

    @Test
    fun given_articleId_when_getArticle_then_returnArticle() {
        // given
        given(articleRepository.findById(anyString())).willReturn(Mono.just(article))
        // when
        val result = service.getArticle("1234")
        // then
        StepVerifier
            .create(result)
            .assertNext { then(it).isEqualTo(article) }
            .expectComplete()
            .verify()
    }
}
