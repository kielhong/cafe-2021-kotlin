package com.widehouse.cafe.cafe.adapter.out.persisitence

import com.widehouse.cafe.cafe.application.port.out.CategoryRepository
import com.widehouse.cafe.cafe.domain.Category
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class CategoryPersistenceAdapter(
    private val categoryMongoRepository: CategoryMongoRepository
) : CategoryRepository {
    override fun loadAllCategory(): Flux<Category> {
        return categoryMongoRepository.findAll()
            .map { it.toDomain() }
    }
}
