package com.widehouse.cafe.comment.application

import com.widehouse.cafe.comment.adapter.`in`.web.dto.CommentRequest
import com.widehouse.cafe.comment.adapter.out.persistence.CommentEntity
import com.widehouse.cafe.comment.adapter.out.persistence.CommentRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

class CommentCommandServiceTest: DescribeSpec ({
    isolationMode = IsolationMode.InstancePerLeaf

    val commentRepository = mockk<CommentRepository>()

    val service = CommentCommandService(commentRepository)

    describe("comment 생성") {
        val request = CommentRequest("articleId", "body")

        context("commentRepository save") {
            val commentEntity = CommentEntity("id", request.articleId, request.body, LocalDateTime.now())
            every { commentRepository.save(any()) } returns Mono.just(commentEntity)
            val result = service.createComment(request)
            it("return created comment") {
                StepVerifier.create(result)
                    .assertNext { it.id shouldBe commentEntity.id }
                    .verifyComplete()
                verify {
                    commentRepository.save(
                        withArg {
                            it.articleId shouldBe request.articleId
                            it.body shouldBe request.body
                        }
                    )
                }
            }
        }
    }
})
