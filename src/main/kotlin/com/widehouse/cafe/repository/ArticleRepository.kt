package com.widehouse.cafe.repository

import com.widehouse.cafe.domain.Article
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ArticleRepository : ReactiveMongoRepository<Article, String> {
}