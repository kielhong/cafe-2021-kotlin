package com.widehouse.cafe.article.application

import com.widehouse.cafe.article.Article
import com.widehouse.cafe.article.ArticleFixtures
import com.widehouse.cafe.article.BoardFixtures
import com.widehouse.cafe.article.adapter.`in`.web.dto.ArticleRequest
import com.widehouse.cafe.article.adapter.out.persistence.ArticleRepository
import com.widehouse.cafe.article.adapter.out.persistence.BoardRepository
import com.widehouse.cafe.common.exception.DataNotFoundException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.BDDAssertions.then
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

internal class ArticleServiceTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val articleRepository = mockk<ArticleRepository>()
    val boardRepository = mockk<BoardRepository>()

    val service = ArticleService(articleRepository, boardRepository)

    val boardId = "board"
    val article1 = Article("1234", boardId, "title1", "body1", LocalDateTime.now())
    val article2 = Article("abcd", boardId, "title2", "body2", LocalDateTime.now())

    describe("articleId가 주어지면 해당하는 article을 한 개 반환") {
        context("articleRepository 가 articleid 에 해당하는 article 반환") {
            // given
            every { articleRepository.findById(article1.id) } returns Mono.just(article1)
            // when
            val result = service.getArticle(article1.id)
            // then
            StepVerifier.create(result)
                .assertNext { it shouldBe article1 }
                .verifyComplete()
            verify { articleRepository.findById(article1.id) }
        }
    }

    describe("boardId 가 주어지면 boardId에 연결된 모든 article목록을 반환") {
        context("boardId에 연결된 모든 article목록을 반환") {
            every { articleRepository.findByBoardId(boardId) } returns Flux.just(article1, article2)
            // when
            val result = service.listByBoard(boardId)
            // then
            StepVerifier.create(result)
                .assertNext { then(it).isEqualTo(article1) }
                .assertNext { then(it).isEqualTo(article2) }
                .verifyComplete()
        }
    }

    describe("cafeId가 주어지면 cafeId에 연결된 모든 article목록을 반환") {
        context("cafeId에 연결된 모든 article목록을 반환") {
            // given
            val cafeId = "test"
            every { boardRepository.findByCafeId(any()) }
                .returns(Flux.just(BoardFixtures.create("1", cafeId), BoardFixtures.create("2", cafeId)))
            every { articleRepository.findByBoardIdIn(any()) } returns Flux.just(article1, article2)
            // when
            val result = service.listByCafe(cafeId)
            // then
            StepVerifier.create(result)
                .assertNext { then(it).isEqualTo(article1) }
                .assertNext { then(it).isEqualTo(article2) }
                .verifyComplete()
        }
    }

    describe("article 생성") {
        // given
        val request = ArticleRequest(boardId = "boardId", title = "title", body = "body")
        val article = Article("id", request.boardId, request.title, request.body, LocalDateTime.now())
        every { articleRepository.save(any()) } returns Mono.just(article)
        // when
        val result = service.create(request)
        // then
        StepVerifier.create(result)
            .assertNext { then(it).isEqualTo(article) }
            .verifyComplete()
    }

    describe("Article 변경") {
        val article = ArticleFixtures.create()
        val request = ArticleRequest("newBoardId", "newTitle", "newBody")

        context("존재하는 article이면 변경된 article 반환") {
            // given
            val updatedArticle = Article(article.id, request.boardId, request.title, request.body, LocalDateTime.now())
            every { articleRepository.findById(article.id) } returns Mono.just(article)
            every { articleRepository.save(any()) } returns (Mono.just(updatedArticle))
            // when
            val result = service.update(article.id, request)
            // then
            StepVerifier.create(result)
                .assertNext { then(it).isEqualTo(updatedArticle) }
                .verifyComplete()
        }

        context("존재하지 않는 article이면 DataNotFoundException 반환") {
            // given
            every { articleRepository.findById(article.id) } returns Mono.empty()
            // when
            val result = service.update(article.id, request)
            // then
            StepVerifier.create(result)
                .expectError(DataNotFoundException::class.java)
                .verify()
        }
    }

    describe("Article 삭제") {
        val article = ArticleFixtures.create()

        context("존재하는 article이면 article 삭제") {
            // given
            every { articleRepository.findById(article.id) } returns Mono.just(article)
            every { articleRepository.deleteById(article.id) } returns (Mono.empty())
            // when
            val result = service.delete(article.id)
            // then
            StepVerifier.create(result)
                .verifyComplete()
            verify { articleRepository.deleteById(article.id) }
        }

        context("존재하지 않는 article이면 DataNotFoundException 반환") {
            // given
            every { articleRepository.findById(article.id) } returns Mono.empty()
            // when
            val result = service.delete(article.id)
            // then
            StepVerifier.create(result)
                .expectError(DataNotFoundException::class.java)
                .verify()
            verify(exactly = 0) { articleRepository.deleteById(article.id) }
        }
    }
})
