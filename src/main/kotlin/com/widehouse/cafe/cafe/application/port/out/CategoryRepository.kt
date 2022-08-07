package com.widehouse.cafe.cafe.application.port.out

import com.widehouse.cafe.cafe.domain.Category
import reactor.core.publisher.Flux

interface CategoryRepository {
    fun loadAllCategory(): Flux<Category>
}
