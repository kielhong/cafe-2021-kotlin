package com.widehouse.cafe.cafe.adapter.`in`.web

import com.widehouse.cafe.cafe.domain.Cafe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class CafeControllerIntTest(
    @Autowired val webClient: WebTestClient,
    @Autowired val template: ReactiveMongoTemplate
) {
    @Test
    fun given_categoryId_then_listByCategory() {
        // when
        val categoryId = 1L
        template.save(Cafe("1", "name1", "desc1", categoryId)).block()
        template.save(Cafe("2", "name2", "desc3", categoryId)).block()
        // then
        webClient.get()
            .uri {
                it.path("/cafe")
                    .queryParam("categoryId", categoryId)
                    .build()
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.size()").isEqualTo(2)
    }
}
