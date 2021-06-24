package com.widehouse.cafe.cafe.repository

import com.widehouse.cafe.cafe.model.Cafe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CafeRepository : ReactiveMongoRepository<Cafe, String>
