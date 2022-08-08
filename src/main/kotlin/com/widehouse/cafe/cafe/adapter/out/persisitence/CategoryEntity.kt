package com.widehouse.cafe.cafe.adapter.out.persisitence

import com.widehouse.cafe.cafe.domain.Category
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CategoryEntity(
    @Id
    val id: Long,
    val name: String,
    val listOrder: Int
) {
    fun toDomain(): Category =
        Category(this.id, this.name, this.listOrder)
}
