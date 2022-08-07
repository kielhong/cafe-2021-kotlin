package com.widehouse.cafe.cafe.adapter.out.persisitence

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CategoryMongoRepository : ReactiveMongoRepository<CategoryEntity, Long>
