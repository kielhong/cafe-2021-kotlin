package com.widehouse.cafe.article.service

import com.widehouse.cafe.article.Article
import com.widehouse.cafe.article.controller.dto.ArticleDto
import com.widehouse.cafe.article.model.Board
import com.widehouse.cafe.article.repository.ArticleRepository
import com.widehouse.cafe.article.repository.BoardRepository
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class ArticleServiceTest {
    private lateinit var service: ArticleService
    @Mock
    private lateinit var articleRepository: ArticleRepository
    @Mock
    private lateinit var boardRepository: BoardRepository

    private lateinit var boardId: String
    private lateinit var article1: Article
    private lateinit var article2: Article

    @BeforeEach
    internal fun setUp() {
        service = ArticleService(articleRepository, boardRepository)

        boardId = "board"
        article1 = Article("1234", boardId, "title1", "body1")
        article2 = Article("abcd", boardId, "title2", "body2")
    }

    @Test
    fun `articleId가 주어지면 해당하는 article을 한 개 반환`() {
        // given
        given(articleRepository.findById(anyString())).willReturn(Mono.just(article1))
        // when
        val result = service.getArticle(article1.id!!)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .verifyComplete()
    }

    @Test
    fun `boardId 가 주어지면 boardId에 연결된 모든 article목록을 반환`() {
        // given
        given(articleRepository.findByBoardId(anyString())).willReturn(Flux.just(article1, article2))
        // when
        val result = service.listByBoard(boardId)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }

    @Test
    fun `cafeId가 주어지면 cafeId에 연결된 모든 article목록을 반환`() {
        // given
        val cafeUrl = "url"
        given(boardRepository.findByCafeId(anyString())).willReturn(Flux.just(Board("1", cafeUrl), Board("2", cafeUrl)))
        given(articleRepository.findByBoardIdIn(anyList())).willReturn(Flux.just(article1, article2))
        // when
        val result = service.listByCafe(cafeUrl)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article1) }
            .assertNext { then(it).isEqualTo(article2) }
            .verifyComplete()
    }

    @Test
    fun `article 생성`() {
        // given
        val request = ArticleDto(boardId = "boardId", title = "title", body = "body")
        val article = Article("id", request.boardId, request.title, request.body)
        given(articleRepository.save(any(Article::class.java)))
            .willReturn(Mono.just(article))
        // when
        var result = service.create(request)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article) }
            .verifyComplete()
    }
}
