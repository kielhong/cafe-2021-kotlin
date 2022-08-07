package com.widehouse.cafe.cafe.adapter.`in`.web

import com.widehouse.cafe.cafe.application.port.`in`.CategoryQueryUseCase
import com.widehouse.cafe.cafe.domain.Category
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("categories")
class CategoryController(
    private val categoryQueryUseCase: CategoryQueryUseCase
) {
    @GetMapping
    fun listAllCategories(): Flux<Category> {
        return categoryQueryUseCase.listCategories()
    }
}
