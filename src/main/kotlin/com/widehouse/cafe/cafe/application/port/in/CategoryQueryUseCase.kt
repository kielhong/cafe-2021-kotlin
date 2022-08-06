package com.widehouse.cafe.cafe.application.port.`in`

import com.widehouse.cafe.cafe.domain.Category
import reactor.core.publisher.Flux

interface CategoryQueryUseCase {
    fun listCategories(): Flux<Category>
}
