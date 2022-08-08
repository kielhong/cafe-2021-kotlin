package com.widehouse.cafe.cafe.application

import com.widehouse.cafe.cafe.application.port.`in`.CategoryQueryUseCase
import com.widehouse.cafe.cafe.application.port.out.CategoryRepository
import com.widehouse.cafe.cafe.domain.Category
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.Comparator.comparing

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) : CategoryQueryUseCase {
    override fun listCategories(): Flux<Category> =
        categoryRepository.loadAllCategory()
            .sort(comparing(Category::listOrder))
}
