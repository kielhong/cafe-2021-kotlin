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
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class ArticleServiceTest {
    lateinit var service: ArticleService
    @Mock
    lateinit var articleRepository: ArticleRepository

    lateinit var boardId: String
    lateinit var article1: Article
    lateinit var article2: Article

    @BeforeEach
    internal fun setUp() {
        service = ArticleService(articleRepository)

        boardId = "board"
        article1 = Article("1234", boardId)
        article2 = Article("abcd", boardId)
    }

    @Test
    fun `articleId가 주어지면 해당하는 article을 한 개 반환`() {
        // given
        given(articleRepository.findById(anyString())).willReturn(Mono.just(article1))
        // when
        val result = service.getArticle(article1.id)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .verifyComplete()
    }

    @Test
    fun `boardId 가 주어지면 boardId에 연결된 모든 article목록을 반환`() {
        // given
        given(articleRepository.findAllByBoardId(anyString())).willReturn(Flux.just(article1, article2))
        // when
        val result = service.listArticleByBoard(boardId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }
}
