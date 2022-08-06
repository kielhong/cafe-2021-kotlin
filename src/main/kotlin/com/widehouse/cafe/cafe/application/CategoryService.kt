package com.widehouse.cafe.cafe.application

import com.widehouse.cafe.cafe.application.port.`in`.CategoryQueryUseCase
import com.widehouse.cafe.cafe.domain.Category
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class CategoryService : CategoryQueryUseCase {
    override fun listCategories(): Flux<Category> {
        TODO("Not yet implemented")
    }
}
